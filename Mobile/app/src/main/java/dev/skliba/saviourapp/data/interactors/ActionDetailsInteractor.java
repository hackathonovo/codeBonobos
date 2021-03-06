package dev.skliba.saviourapp.data.interactors;

import android.support.annotation.Nullable;

import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.models.response.ActionDetailsResponse;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.network.ApiService;
import dev.skliba.saviourapp.data.network.BaseCallback;
import dev.skliba.saviourapp.util.SharedPrefsUtil;
import retrofit2.Call;
import retrofit2.Response;

public class ActionDetailsInteractor {

    private ApiService apiService;

    private BaseCallback<BaseResponse<ActionDetailsResponse>> callback;

    private Call<BaseResponse<ActionDetailsResponse>> call;

    private BaseCallback<BaseResponse<Void>> acceptCallback;

    private Call<BaseResponse<Void>> acceptCall;

    public ActionDetailsInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public void execute(String actionId, Listener<ActionDetailsResponse> listener) {

        call = apiService.getActionDetails(actionId);

        callback = new BaseCallback<BaseResponse<ActionDetailsResponse>>() {
            @Override
            public void onSuccess(BaseResponse<ActionDetailsResponse> body, Response<BaseResponse<ActionDetailsResponse>> response) {
                listener.onSuccess(body.getResponse());
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

    public void onAcceptClicked(String actionId, Listener<BaseResponse<Void>> listener) {

        acceptCall = apiService.onActionResponse(actionId, SharedPrefsUtil.getUserId(), true);

        acceptCallback = new BaseCallback<BaseResponse<Void>>() {
            @Override
            public void onSuccess(BaseResponse<Void> body, Response<BaseResponse<Void>> response) {
                listener.onSuccess(body);
            }

            @Override
            public void onUnknownError(@Nullable String error) {
                listener.onFailure(error);
            }
        };

        acceptCall.enqueue(acceptCallback);

    }

    public void onDeclineClicked(String actionId, Listener<BaseResponse<Void>> listener) {

        acceptCall = apiService.onActionResponse(actionId, SharedPrefsUtil.getUserId(), false);

        acceptCallback = new BaseCallback<BaseResponse<Void>>() {
            @Override
            public void onSuccess(BaseResponse<Void> body, Response<BaseResponse<Void>> response) {
                listener.onSuccess(body);
            }

            @Override
            public void onUnknownError(@Nullable String error) {
                listener.onFailure(error);
            }
        };

        acceptCall.enqueue(acceptCallback);
    }
}