package dev.skliba.guardianangel.di;


import android.content.Context;

import dev.skliba.guardianangel.data.managers.FacebookManager;

public class ManagerFactory {

    private ManagerFactory() {
    }

    public static FacebookManager provideManager(Context context) {
        return new FacebookManager(context);
    }
}
