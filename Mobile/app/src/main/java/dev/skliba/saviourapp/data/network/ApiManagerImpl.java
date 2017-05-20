package dev.skliba.saviourapp.data.network;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManagerImpl implements ApiManager {

    private static final String API_ENDPOINT = "http://192.168.201.44:8080/api/";

    private ApiService apiService;

    private static ApiManagerImpl instance;

    public static synchronized ApiManagerImpl getInstance() {
        if (instance == null) {
            instance = new ApiManagerImpl();
        }
        return instance;
    }

    private ApiManagerImpl() {
    }

    public void init() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        setup(client);
    }

    private void setup(OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        apiService = builder.build().create(ApiService.class);
    }

    @Override
    public ApiService getService() {
        return apiService;
    }
}
