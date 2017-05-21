package dev.skliba.guardianangel.ui.dashboard.news;


import java.util.List;

import dev.skliba.guardianangel.data.models.response.NewsResponse;
import dev.skliba.guardianangel.ui.shared.BaseMvp;

public interface NewsMvp {

    interface Presenter extends BaseMvp.Presenter {

        void fetchNews();
    }

    interface View extends BaseMvp.View {

        void initUi(List<NewsResponse> news);
    }
}
