package dev.skliba.saviourapp.ui.login;


import dev.skliba.saviourapp.data.managers.FacebookManager;
import dev.skliba.saviourapp.ui.shared.BaseMvp;

public interface LoginMvp {

    interface Presenter extends BaseMvp.Presenter {

        void onFacebookLoginClicked(FacebookManager facebookManager);

        void onNativeLoginClicked(String username, String password);
    }

    interface View extends BaseMvp.View {

        void navigateToMain();

        void showEmptyUsername(String error);

        void showEmptyPassword(String error);
    }
}