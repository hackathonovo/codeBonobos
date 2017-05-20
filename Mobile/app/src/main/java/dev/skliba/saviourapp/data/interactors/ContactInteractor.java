package dev.skliba.saviourapp.data.interactors;

import android.support.annotation.Nullable;

import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.models.response.ContactResponse;
import dev.skliba.saviourapp.data.network.ApiService;
import dev.skliba.saviourapp.data.network.BaseCallback;
import dev.skliba.saviourapp.util.SharedPrefsUtil;
import retrofit2.Call;
import retrofit2.Response;

public class ContactInteractor {

    private ApiService apiService;

    private BaseCallback<BaseResponse<ContactResponse>> callback;

    private Call<BaseResponse<ContactResponse>> call;

    public ContactInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public void fetchActions(Listener<ContactResponse> listener) {

        if (SharedPrefsUtil.getUserId() == -1) {
            listener.onFailure("User isn't logged in");
            return;
        }

        call = apiService.checkIfIHaveAnyActions(SharedPrefsUtil.getUserId());
        callback = new BaseCallback<BaseResponse<ContactResponse>>() {
            @Override
            public void onSuccess(BaseResponse<ContactResponse> body, Response<BaseResponse<ContactResponse>> response) {
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
}