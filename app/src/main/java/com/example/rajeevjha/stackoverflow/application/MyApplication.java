package com.example.rajeevjha.stackoverflow.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApplication extends Application {

    private static final String LOG_TAG = MyApplication.class.getSimpleName();
    public static MyApplication myApplication;
    private static Context sApplicationContext = null;
    private static SharedPreferences preferences;
    // Needs to be volatile as another thread can see a half initialised instance.
    private volatile static MyApplication applicationInstance;

    public static Context getContext() {
        return sApplicationContext;
    }

    public static void setContext(Context newContext) {
        sApplicationContext = newContext;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Application singleton
        applicationInstance = this;
        sApplicationContext = getApplicationContext();

        preferences = sApplicationContext.getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);
    }
}
