package dev.skliba.guardianangel.ui.dashboard.profile;

import dev.skliba.guardianangel.ui.shared.BaseMvp;

public interface ProfileMvp {

    interface Presenter extends BaseMvp.Presenter {

        void signOut();
    }

    interface View extends BaseMvp.View {

        void navigateToLogin();
    }
}