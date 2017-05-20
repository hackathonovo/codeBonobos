package dev.skliba.saviourapp.ui.dashboard.contact;

import dev.skliba.saviourapp.ui.shared.BaseMvp;

public interface ContactMvp {

    interface Presenter extends BaseMvp.Presenter {

        void init();
    }

    interface View extends BaseMvp.View {

        void loadWebViewUrl(String url);

        void showEmptyUi();
    }
}