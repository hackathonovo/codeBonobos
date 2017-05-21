package dev.skliba.saviourapp.ui.dashboard.profile;

import dev.skliba.saviourapp.ui.shared.BaseMvp;

public interface ProfileMvp {

    interface Presenter extends BaseMvp.Presenter {

        void signOut();

        void onUserAvailable(boolean isAvailable);
    }

    interface View extends BaseMvp.View {

        void navigateToLogin();

        void success();
    }
}