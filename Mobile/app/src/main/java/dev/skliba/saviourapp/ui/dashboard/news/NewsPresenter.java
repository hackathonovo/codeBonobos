package dev.skliba.saviourapp.ui.dashboard.news;


import java.util.List;

import dev.skliba.saviourapp.data.interactors.NewsInteractor;
import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.models.response.NewsResponse;

public class NewsPresenter implements NewsMvp.Presenter {

    private NewsMvp.View view;

    private NewsInteractor interactor;

    public NewsPresenter(NewsMvp.View view, NewsInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void fetchNews() {
        view.showProgress();

        interactor.execute(listener);
    }

    private Listener<BaseResponse<List<NewsResponse>>> listener = new Listener<BaseResponse<List<NewsResponse>>>() {
        @Override
        public void onSuccess(BaseResponse<List<NewsResponse>> listBaseResponse) {
            view.initUi(listBaseResponse.getResponse());
        }

        @Override
        public void onFailure(String message) {
            view.showError(message);
        }

        @Override
        public void onConnectionFailure(String message) {
            //nothing here
        }
    };

    @Override
    public void cancel() {
        interactor.cancel();
    }

}