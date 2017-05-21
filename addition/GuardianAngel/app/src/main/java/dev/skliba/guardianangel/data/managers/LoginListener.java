package dev.skliba.guardianangel.data.managers;


public interface LoginListener {

    void onSuccess(String accessToken);

    void onCancelled();

    void onError(String message);
}
