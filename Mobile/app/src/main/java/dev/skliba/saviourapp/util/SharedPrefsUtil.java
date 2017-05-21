package dev.skliba.saviourapp.util;


import android.preference.PreferenceManager;

import dev.skliba.saviourapp.SaviourApplication;


public class SharedPrefsUtil {

    private static final String ACCESS_TOKEN = "access_token";

    private static final String USER_ID = "user_id";

    private static final String PANIC_KEYWORD = "panic_keyword";

    private static final String OK_KEYWORD = "ok_keyword";

    private static final String FIRST_PANIC_NUMBER = "first_num";

    private static final String SECOND_PANIC_NUMBER = "second_num";

    private static final String FIREBASE_TOKEN = "firebase_token";

    public static boolean isLoggedIn() {
        return PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).contains(ACCESS_TOKEN) || PreferenceManager
                .getDefaultSharedPreferences(SaviourApplication.getInstance()).contains(USER_ID);
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

    public static void setPanicKeyword(String panicKeyword) {
        PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).edit().putString(PANIC_KEYWORD, panicKeyword)
                .apply();
    }

    public static void setOkKeyword(String okKeyword) {
        PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).edit().putString(OK_KEYWORD, okKeyword).apply();
    }

    public static String panicKeyword() {
        return PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).getString(PANIC_KEYWORD, "");
    }

    public static String okKeyword() {
        return PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).getString(OK_KEYWORD, "");
    }

    public static String getFirstPanicNumber() {
        return PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).getString(FIRST_PANIC_NUMBER, "");
    }

    public static String getSecondPanicNumber() {
        return PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).getString(SECOND_PANIC_NUMBER, "");
    }

    public static void setFirstPanicNumber(String firstPanicNumber) {
        PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).edit()
                .putString(FIRST_PANIC_NUMBER, firstPanicNumber).apply();
    }

    public static void setSecondPanicNumber(String secondPanicNumber) {
        PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).edit()
                .putString(SECOND_PANIC_NUMBER, secondPanicNumber).apply();
    }

    public static void storeFirebaseToken(String refreshedToken) {
        PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).edit()
                .putString(FIREBASE_TOKEN, refreshedToken).apply();
    }

    public static String getFirebaseToken() {
        return PreferenceManager.getDefaultSharedPreferences(SaviourApplication.getInstance()).getString(FIREBASE_TOKEN, "");
    }
}
