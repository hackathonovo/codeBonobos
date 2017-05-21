package dev.skliba.saviourapp.data.network;

import java.util.List;

import dev.skliba.saviourapp.data.models.response.ActionDetailsResponse;
import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.models.response.ContactResponse;
import dev.skliba.saviourapp.data.models.response.LoginResponse;
import dev.skliba.saviourapp.data.models.response.NewsResponse;
import dev.skliba.saviourapp.data.models.response.PanicModeResponse;
import dev.skliba.saviourapp.data.models.response.ProfileResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("news")
    Call<BaseResponse<List<NewsResponse>>> getNews();

    @GET("profile/{id}")
    Call<BaseResponse<ProfileResponse>> fetchUserProfile(@Path("id") int id);

    @GET("spasavatelji/login")
    Call<BaseResponse<LoginResponse>> nativeLogin(@Query("username") String username, @Query("password") String password,
            @Query("firebaseToken") String firebaseToken);

    @GET("spasavatelji/fb/{accessToken}")
    Call<BaseResponse<LoginResponse>> facebookLogin(@Path("accessToken") String accessToken, @Query("name") String name);

    @GET("akcije/active/{userId}")
    Call<BaseResponse<ContactResponse>> checkIfIHaveAnyActions(@Path("userId") int userId);

    Call<BaseResponse<PanicModeResponse>> panicModeEngaged(@Path("userId") int userId, @Query("lat") double latitude,
            @Query("lng") double longitude, @Query("timestamp") long ms);

    @GET("akcije/{actionId}")
    Call<BaseResponse<ActionDetailsResponse>> getActionDetails(@Path("actionId") String actionId);

    @GET("spasavatelji/answer-invite")
    Call<BaseResponse<Void>> onActionResponse(@Query("actionId") String actionId, @Query("userId") int userId,
            @Query("answer") boolean isAccepted);

    @GET("spasavatelji/location/{userId}")
    Call<BaseResponse<Void>> sendUserLocation(@Path("userId") int userId, @Query("lat") double lat, @Query("lng") double lng,
            @Query("timestamp") long ms);

    @GET("spasavatelji/dostupnost/{userId}")
    Call<BaseResponse<Void>> setUserAccessability(@Path("userId") int userId, @Query("isAccessible") boolean isAccessible);
}
