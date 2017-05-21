package dev.skliba.guardianangel.data.interactors;

import android.support.annotation.Nullable;

import java.util.List;

import dev.skliba.guardianangel.data.listeners.Listener;
import dev.skliba.guardianangel.data.models.response.BaseResponse;
import dev.skliba.guardianangel.data.models.response.NewsResponse;
import dev.skliba.guardianangel.data.network.ApiService;
import dev.skliba.guardianangel.data.network.BaseCallback;
import retrofit2.Call;
import retrofit2.Response;

public class NewsInteractor {

    private ApiService apiService;

    private BaseCallback<BaseResponse<List<NewsResponse>>> callback;

    private Call<BaseResponse<List<NewsResponse>>> call;

    public NewsInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public void execute(Listener<BaseResponse<List<NewsResponse>>> listener) {

        call = apiService.getNews();
        callback = new BaseCallback<BaseResponse<List<NewsResponse>>>() {
            @Override
            public void onSuccess(BaseResponse<List<NewsResponse>> body, Response<BaseResponse<List<NewsResponse>>> response) {
                listener.onSuccess(body);
            }

            @Override
            public void onUnknownError(@Nullable String error) {
                listener.onFailure(error);
            }
        };

        call.enqueue(callback);
    }

    public void cancel() {
        if (call != null && callback != null) {
            call.cancel();
            callback.cancel();
        }
    }
}