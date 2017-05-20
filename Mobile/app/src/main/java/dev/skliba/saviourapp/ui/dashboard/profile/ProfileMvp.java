package dev.skliba.saviourapp.ui.dashboard.profile;

import dev.skliba.saviourapp.ui.shared.BaseMvp;

public interface ProfileMvp {

    interface Presenter extends BaseMvp.Presenter {

        void signOut();
    }

    interface View extends BaseMvp.View {

        void navigateToLogin();
    }
}