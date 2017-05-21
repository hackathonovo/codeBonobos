package dev.skliba.guardianangel.data.listeners;


public interface Listener<Type> {

    void onSuccess(Type type);

    void onFailure(String message);

    //applies to the cases where internet is not accessible or not turned on
    //or for some hardware reasons connection to the internet could not be established
    void onConnectionFailure(String message);
}
