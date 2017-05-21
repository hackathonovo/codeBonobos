package dev.skliba.saviourapp.ui.dashboard.profile;


import dev.skliba.saviourapp.data.interactors.ProfileInteractor;
import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.managers.FacebookManager;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.util.SharedPrefsUtil;

public class ProfilePresenter implements ProfileMvp.Presenter {

    private ProfileMvp.View view;

    private ProfileInteractor interactor;

    public ProfilePresenter(ProfileMvp.View view, ProfileInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void signOut() {
        SharedPrefsUtil.signOutUser();
        FacebookManager.logout();
        view.navigateToLogin();
    }

    @Override
    public void onUserAvailable(boolean isAvailable) {
        interactor.isUserAvailable(isAvailable, listener);
    }

    private Listener<BaseResponse<Void>> listener = new Listener<BaseResponse<Void>>() {
        @Override
        public void onSuccess(BaseResponse<Void> aVoid) {
            view.success();
        }

        @Override
        public void onFailure(String message) {
            view.showError(message);
        }

        @Override
        public void onConnectionFailure(String message) {
            //
        }
    };

    @Override
    public void cancel() {
        interactor.cancel();
    }
}