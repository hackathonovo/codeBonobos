package dev.skliba.saviourapp.ui.login;

import android.text.TextUtils;

import dev.skliba.saviourapp.data.interactors.LoginInteractor;
import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.managers.FacebookManager;
import dev.skliba.saviourapp.data.managers.FacebookSessionNotInProgress;
import dev.skliba.saviourapp.data.managers.LoginListener;
import dev.skliba.saviourapp.data.models.ordinary.FacebookProfile;
import dev.skliba.saviourapp.data.models.response.LoginResponse;
import dev.skliba.saviourapp.util.SharedPrefsUtil;
import timber.log.Timber;

public class LoginPresenter implements LoginMvp.Presenter {

    private LoginMvp.View view;

    private LoginInteractor interactor;

    public LoginPresenter(LoginMvp.View view, LoginInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onFacebookLoginClicked(FacebookManager facebookManager) {
        facebookManager.login(new LoginListener() {
            @Override
            public void onSuccess(String accessToken) {
                FacebookProfile profile = FacebookManager.getProfile();
                if (profile != null) {
                    interactor.facebookLogin(accessToken, profile.getFirstName() + profile.getLastName(), fbListener);
                }
            }

            @Override
            public void onCancelled() {
                view.showError("Facebook login cancelled");
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }

    @Override
    public void onNativeLoginClicked(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            view.showEmptyUsername("Empty username");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            view.showEmptyPassword("Empty password");
            return;
        }

        view.showProgress();
        interactor.nativeLogin(username, password, nativeListener);
    }

    private Listener<LoginResponse> fbListener = new Listener<LoginResponse>() {
        @Override
        public void onSuccess(LoginResponse loginResponse) {
            try {
                SharedPrefsUtil.storeToken(FacebookManager.getAccessToken());
            } catch (FacebookSessionNotInProgress facebookSessionNotInProgress) {
                Timber.e(facebookSessionNotInProgress);
            }
            SharedPrefsUtil.setUserId(loginResponse.getUserId());
            view.navigateToMain();
        }

        @Override
        public void onFailure(String message) {
            SharedPrefsUtil.signOutUser();
            view.showError(message);
        }

        @Override
        public void onConnectionFailure(String message) {
            //nothing here
        }
    };

    private Listener<LoginResponse> nativeListener = new Listener<LoginResponse>() {
        @Override
        public void onSuccess(LoginResponse loginResponse) {
            view.hideProgress();
            if (loginResponse != null) {
                SharedPrefsUtil.setUserId(loginResponse.getUserId());
                view.navigateToMain();
            }
        }

        @Override
        public void onFailure(String message) {
            view.hideProgress();
            view.showError(message);
        }

        @Override
        public void onConnectionFailure(String message) {
            //nothing
        }
    };

    @Override
    public void cancel() {
        interactor.cancel();
    }
}