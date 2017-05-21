package dev.skliba.guardianangel.data.interactors;

import android.support.annotation.Nullable;

import dev.skliba.guardianangel.data.listeners.Listener;
import dev.skliba.guardianangel.data.models.response.BaseResponse;
import dev.skliba.guardianangel.data.models.response.ProfileResponse;
import dev.skliba.guardianangel.data.network.ApiService;
import dev.skliba.guardianangel.data.network.BaseCallback;
import retrofit2.Call;
import retrofit2.Response;

public class ProfileInteractor {

    private ApiService apiService;

    private BaseCallback<BaseResponse<ProfileResponse>> callback;

    private Call<BaseResponse<ProfileResponse>> call;

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
}