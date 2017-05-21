package dev.skliba.guardianangel.data.network;


import java.util.List;

import dev.skliba.guardianangel.data.models.response.BaseResponse;
import dev.skliba.guardianangel.data.models.response.LoginResponse;
import dev.skliba.guardianangel.data.models.response.NewsResponse;
import dev.skliba.guardianangel.data.models.response.ProfileResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/news")
    Call<BaseResponse<List<NewsResponse>>> getNews();

    @GET("/api/profile/{id}")
    Call<BaseResponse<ProfileResponse>> fetchUserProfile(@Path("id") int id);

    @GET("spasavatelji/login")
    Call<BaseResponse<LoginResponse>> nativeLogin(@Query("username") String username, @Query("password") String password,
            @Query("firebaseToken") String firebaseToken);

    Call<BaseResponse<LoginResponse>> facebookLogin(String accessToken);

    @GET("spasavatelji/location/{userId}")
    Call<BaseResponse<Void>> sendUserLocation(@Path("userId") int userId, @Query("lat") double lat, @Query("lng") double lng,
            @Query("timestamp") long ms);
}
