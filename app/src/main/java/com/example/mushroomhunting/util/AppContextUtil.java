package com.example.mushroomhunting.util;

import android.app.Application;
import android.content.Context;

public class AppContextUtil extends Application {
    private static AppContextUtil instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}