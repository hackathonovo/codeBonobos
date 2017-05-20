package dev.skliba.saviourapp.ui.dashboard.profile;


import dev.skliba.saviourapp.data.interactors.ProfileInteractor;
import dev.skliba.saviourapp.data.managers.FacebookManager;
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
    public void cancel() {
        interactor.cancel();
    }
}