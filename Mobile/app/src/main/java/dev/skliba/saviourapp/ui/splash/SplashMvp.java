package dev.skliba.saviourapp.ui.splash;


import dev.skliba.saviourapp.ui.shared.BaseMvp;

public interface SplashMvp {

    interface View extends BaseMvp.View {

        void navigateToMain();

        void navigateToLogin();
    }

    interface Presenter extends BaseMvp.Presenter {

        void checkUserLoggedIn();
    }
}
