package dev.skliba.saviourapp;


import com.facebook.FacebookSdk;

import android.app.Application;
import android.content.Intent;

import dev.skliba.saviourapp.data.network.ApiManagerImpl;
import dev.skliba.saviourapp.data.network.ApiService;
import dev.skliba.saviourapp.ui.push.GAFirebaseInstanceIdService;
import dev.skliba.saviourapp.ui.push.GAFirebaseMessagingService;
import timber.log.Timber;

public class SaviourApplication extends Application {

    protected static SaviourApplication instance;

    protected ApiService apiService;

    public static SaviourApplication getInstance() {
        return instance;
    }

    public static void setInstance(SaviourApplication instance) {
        SaviourApplication.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        FacebookSdk.sdkInitialize(this);

        Timber.plant(new Timber.DebugTree());

        ApiManagerImpl.getInstance().init();
        apiService = ApiManagerImpl.getInstance().getService();

        startService(new Intent(this, GAFirebaseInstanceIdService.class));
        startService(new Intent(this, GAFirebaseMessagingService.class));
    }

    public static ApiService getApiService() {
        return getInstance().apiService;
    }

}
