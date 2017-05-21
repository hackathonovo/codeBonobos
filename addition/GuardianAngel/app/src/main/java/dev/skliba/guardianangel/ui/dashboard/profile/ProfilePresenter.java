package dev.skliba.guardianangel.ui.dashboard.profile;

import dev.skliba.guardianangel.data.interactors.ProfileInteractor;
import dev.skliba.guardianangel.data.managers.FacebookManager;
import dev.skliba.guardianangel.utils.SharedPrefsUtil;

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