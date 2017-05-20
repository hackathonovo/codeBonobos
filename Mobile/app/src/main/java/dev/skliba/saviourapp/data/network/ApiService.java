package dev.skliba.saviourapp.data.network;

import java.util.List;

import dev.skliba.saviourapp.data.models.response.BaseResponse;
import dev.skliba.saviourapp.data.models.response.ContactResponse;
import dev.skliba.saviourapp.data.models.response.LoginResponse;
import dev.skliba.saviourapp.data.models.response.NewsResponse;
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
    Call<BaseResponse<LoginResponse>> nativeLogin(@Query("username") String username, @Query("password") String password);

    @GET("spasavatelji/fb/{accessToken}")
    Call<BaseResponse<LoginResponse>> facebookLogin(@Path("accessToken") String accessToken, @Query("name") String name);

    @GET("akcije/{userId}")
    Call<BaseResponse<ContactResponse>> checkIfIHaveAnyActions(@Path("userId") int userId);
}
