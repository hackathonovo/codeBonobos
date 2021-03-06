package dev.skliba.guardianangel.ui.splash;


import dev.skliba.guardianangel.data.interactors.SplashInteractor;
import dev.skliba.guardianangel.utils.SharedPrefsUtil;

public class SplashPresenter implements SplashMvp.Presenter {

    private final SplashMvp.View view;

    private final SplashInteractor splashInteractor;

    public SplashPresenter(SplashMvp.View view, SplashInteractor splashInteractor) {
        this.view = view;
        this.splashInteractor = splashInteractor;
    }

    @Override
    public void checkUserLoggedIn() {
        if (SharedPrefsUtil.isLoggedIn()) {
            view.navigateToMain();
        } else {
            view.navigateToLogin();
        }
    }

    @Override
    public void cancel() {

    }
}
