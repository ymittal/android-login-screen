package com.example.ymittal.backendlesslogin;

import android.app.Application;

import com.backendless.Backendless;

public class YourBackendlessApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        Backendless.setUrl("https://api.backendless.com");
        Backendless.initApp(this,
                getString(R.string.YOUR_APPLICATION_ID),
                getString(R.string.YOUR_ANDROID_SECRET_KEY),
                "v1");
    }
}