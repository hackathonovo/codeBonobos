package dev.skliba.saviourapp.ui.splash;


import dev.skliba.saviourapp.data.interactors.SplashInteractor;
import dev.skliba.saviourapp.util.SharedPrefsUtil;

public class SplashPresenter implements SplashMvp.Presenter {

    private final SplashMvp.View view;

    private final SplashInteractor splashInteractor;

    public SplashPresenter(SplashMvp.View view, SplashInteractor splashInteractor) {
        this.view = view;
        this.splashInteractor = splashInteractor;
    }

    @Override
    public void checkUserLoggedIn() {
        if(SharedPrefsUtil.isLoggedIn()) {
            view.navigateToMain();
        }
        else {
            view.navigateToLogin();
        }
    }

    @Override
    public void cancel() {

    }
}
