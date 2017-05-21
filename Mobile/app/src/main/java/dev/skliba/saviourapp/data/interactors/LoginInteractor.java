package dev.skliba.saviourapp.data.interactors;

import android.support.annotation.Nullable;

import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.models.response.LoginResponse;
import dev.skliba.saviourapp.data.network.ApiService;
import dev.skliba.saviourapp.data.network.BaseCallback;
import dev.skliba.saviourapp.util.SharedPrefsUtil;
import retrofit2.Call;
import retrofit2.Response;

public class LoginInteractor {

    private ApiService apiService;

    private BaseCallback<BaseResponse<LoginResponse>> callback;

    private Call<BaseResponse<LoginResponse>> call;

    public LoginInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public void nativeLogin(String username, String password, Listener<LoginResponse> listener) {

        call = apiService.nativeLogin(username, password, SharedPrefsUtil.getFirebaseToken());

        callback = new BaseCallback<BaseResponse<LoginResponse>>() {
            @Override
            public void onSuccess(BaseResponse<LoginResponse> body, Response<BaseResponse<LoginResponse>> response) {
                listener.onSuccess(body.getResponse());
            }

            @Override
            public void onUnknownError(@Nullable String error) {
                listener.onFailure(error);
            }
        };

        call.enqueue(callback);
    }

    public void facebookLogin(String accessToken, String name, Listener<LoginResponse> listener) {

        call = apiService.facebookLogin(accessToken, name);

        callback = new BaseCallback<BaseResponse<LoginResponse>>() {
            @Override
            public void onSuccess(BaseResponse<LoginResponse> body, Response<BaseResponse<LoginResponse>> response) {
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