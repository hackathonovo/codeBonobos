package dev.skliba.saviourapp.ui.dashboard.news;


import java.util.List;

import dev.skliba.saviourapp.data.models.response.NewsResponse;
import dev.skliba.saviourapp.ui.shared.BaseMvp;

public interface NewsMvp {

    interface Presenter extends BaseMvp.Presenter {

        void fetchNews();
    }

    interface View extends BaseMvp.View {

        void initUi(List<NewsResponse> news);
    }
}
