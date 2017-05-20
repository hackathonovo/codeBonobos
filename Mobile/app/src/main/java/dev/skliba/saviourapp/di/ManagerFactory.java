package dev.skliba.saviourapp.di;


import android.content.Context;

import dev.skliba.saviourapp.data.managers.FacebookManager;

public class ManagerFactory {

    private ManagerFactory() {
    }

    public static FacebookManager provideManager(Context context) {
        return new FacebookManager(context);
    }
}
