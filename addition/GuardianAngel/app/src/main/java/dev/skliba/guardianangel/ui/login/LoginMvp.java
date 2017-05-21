package dev.skliba.guardianangel.ui.login;

import dev.skliba.guardianangel.data.managers.FacebookManager;
import dev.skliba.guardianangel.ui.shared.BaseMvp;

public interface LoginMvp {

    interface Presenter extends BaseMvp.Presenter {

        void onFacebookLoginClicked(FacebookManager facebookManager);

        void onNativeLoginClicked(String username, String password);
    }

    interface View extends BaseMvp.View {

        void navigateToMain();

        void showEmptyUsername(String s);

        void showEmptyPassword(String s);
    }
}