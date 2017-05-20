package dev.skliba.saviourapp.util;


import android.preference.PreferenceManager;

import dev.skliba.saviourapp.SaviourApplication;


public class SharedPrefsUtil {

    private static final String ACCESS_TOKEN = "access_token";

    private static final String USER_ID = "user_id";

    public static boolean isLoggedIn() {
        return PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).contains(ACCESS_TOKEN);
    }

    public static String getAccessToken() {
        return PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).getString(ACCESS_TOKEN, "");
    }

    public static void storeToken(String token) {
        PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).edit().putString(ACCESS_TOKEN, token).apply();
    }

    public static void signOutUser() {
        PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).edit().remove(ACCESS_TOKEN).apply();
    }

    public static int getUserId() {
        return PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).getInt(USER_ID, -1);
    }

    public static void setUserId(int userId) {
        PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).edit().putInt(USER_ID, userId).apply();
    }
}
