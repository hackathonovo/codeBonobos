package dev.skliba.guardianangel.data.managers;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.Collections;
import java.util.List;

import dev.skliba.guardianangel.data.models.ordinary.FacebookProfile;

public class FacebookManager {

    private static final List<String> permissions = Collections.singletonList("email");

    private final CallbackManager callbackManager;

    private boolean isCanceled;

    private final Context context;

    public FacebookManager(Context context) {
        this.context = context;
        callbackManager = CallbackManager.Factory.create();
    }

    /**
     * Login in user using Facebook SDK.
     *
     * @param listener Callback listener.
     */
    public void login(final LoginListener listener) {
        reset();

        try {
            String accessToken = getAccessToken();

            if (!TextUtils.isEmpty(accessToken) && verifyCallbackListener(listener)) {
                listener.onSuccess(accessToken);
            } else if (verifyCallbackListener(listener)) {
                listener.onError("Facebook login failed.");
            }
        } catch (FacebookSessionNotInProgress e) {
            // there is no active FB session, login the user.
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        public void onSuccess(LoginResult loginResult) {
                            if (loginResult.getRecentlyDeniedPermissions().isEmpty()) {
                                if (verifyCallbackListener(listener)) {
                                    listener.onSuccess(loginResult.getAccessToken().getToken());
                                }
                            } else {
                                // Logout the user so that we clear the access token.
                                logout();

                                if (verifyCallbackListener(listener)) {
                                    listener.onError("Facebook denied email");
                                }
                            }
                        }

                        public void onCancel() {
                            if (verifyCallbackListener(listener)) {
                                listener.onCancelled();
                            }
                        }

                        public void onError(FacebookException exception) {
                            if (verifyCallbackListener(listener)) {
                                listener.onError(exception.getMessage());
                            }
                        }
                    });

            LoginManager.getInstance().logInWithReadPermissions((Activity) context, permissions);
        }
    }

    public boolean isLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    /**
     * Logs out the user from Facebook session.
     */
    public static void logout() {
        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }
    }

    /**
     * Get Facebook access token of current user's session.
     *
     * @return Facebook token if user is logged in, empty string otherwise.
     */
    public static String getAccessToken() throws FacebookSessionNotInProgress {
        if (AccessToken.getCurrentAccessToken() != null) {
            return AccessToken.getCurrentAccessToken().getToken();
        } else {
            throw new FacebookSessionNotInProgress("There is no active Facebook session.");
        }
    }

    public FacebookProfile getProfile() throws FacebookSessionNotInProgress {
        Profile profile = Profile.getCurrentProfile();

        if (profile == null) {
            throw new FacebookSessionNotInProgress("There is no active Facebook session.");
        } else {
            return new FacebookProfile(profile.getId(), profile.getFirstName(), profile.getLastName(), profile.getMiddleName(),
                    profile.getName(), profile.getLinkUri());
        }
    }

    public <T> void handleActivityResult(int requestCode, int resultCode, T data) {
        if (!(data instanceof Intent)) {
            throw new IllegalStateException("T must be an instance of Intent!");
        }

        callbackManager.onActivityResult(requestCode, resultCode, (Intent) data);
    }

    public void cancel() {
        isCanceled = true;
    }

    private void reset() {
        isCanceled = false;
    }

    private <T> boolean verifyCallbackListener(T listener) {
        return !isCanceled && listener != null;
    }

}
