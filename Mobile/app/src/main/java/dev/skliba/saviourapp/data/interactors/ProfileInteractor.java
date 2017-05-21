package dev.skliba.saviourapp.data.interactors;

import android.support.annotation.Nullable;

import dev.skliba.saviourapp.data.listeners.Listener;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.models.response.ProfileResponse;
import dev.skliba.saviourapp.data.network.ApiService;
import dev.skliba.saviourapp.data.network.BaseCallback;
import dev.skliba.saviourapp.util.SharedPrefsUtil;
import retrofit2.Call;
import retrofit2.Response;


public class ProfileInteractor {

    private ApiService apiService;

    private BaseCallback<BaseResponse<ProfileResponse>> callback;

    private Call<BaseResponse<ProfileResponse>> call;

    private BaseCallback<BaseResponse<Void>> availableCallback;

    private Call<BaseResponse<Void>> availableCall;

    public ProfileInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    public void execute(int profileId, Listener<BaseResponse<ProfileResponse>> listener) {

        call = apiService.fetchUserProfile(profileId);

        callback = new BaseCallback<BaseResponse<ProfileResponse>>() {
            @Override
            public void onSuccess(BaseResponse<ProfileResponse> body, Response<BaseResponse<ProfileResponse>> response) {
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

    public void isUserAvailable(boolean isAvailable, Listener<BaseResponse<Void>> listener) {

        availableCall = apiService.setUserAccessability(SharedPrefsUtil.getUserId(), isAvailable);

        availableCallback = new BaseCallback<BaseResponse<Void>>() {
            @Override
            public void onSuccess(BaseResponse<Void> body, Response<BaseResponse<Void>> response) {
                listener.onSuccess(body);
            }

            @Override
            public void onUnknownError(@Nullable String error) {
                listener.onFailure(error);
            }
        };

        availableCall.enqueue(availableCallback);
    }
}