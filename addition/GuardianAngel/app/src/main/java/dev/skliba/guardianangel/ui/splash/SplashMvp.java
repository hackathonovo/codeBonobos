package dev.skliba.guardianangel.ui.splash;


import dev.skliba.guardianangel.ui.shared.BaseMvp;

public interface SplashMvp {

    interface View extends BaseMvp.View {

        void navigateToMain();

        void navigateToLogin();
    }

    interface Presenter extends BaseMvp.Presenter {

        void checkUserLoggedIn();
    }
}
