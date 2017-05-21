package dev.skliba.guardianangel;


import com.facebook.FacebookSdk;

import android.app.Application;
import android.content.Intent;

import dev.skliba.guardianangel.data.network.ApiManagerImpl;
import dev.skliba.guardianangel.data.network.ApiService;
import dev.skliba.guardianangel.ui.push.GAFirebaseInstanceIdService;
import dev.skliba.guardianangel.ui.push.GAFirebaseMessagingService;

public class GuardianAngelApp extends Application {

    protected static GuardianAngelApp instance;

    protected ApiService apiService;

    public static GuardianAngelApp getInstance() {
        return instance;
    }

    public static void setInstance(GuardianAngelApp instance) {
        GuardianAngelApp.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        FacebookSdk.sdkInitialize(this);

        ApiManagerImpl.getInstance().init();
        apiService = ApiManagerImpl.getInstance().getService();

        startService(new Intent(this, GAFirebaseInstanceIdService.class));
        startService(new Intent(this, GAFirebaseMessagingService.class));
    }

    public static ApiService getApiService() {
        return getInstance().apiService;
    }
}
