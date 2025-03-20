package com.prd.moviesaffinity;

import static com.prd.moviesaffinity.utils.ModeStorage.getMode;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getMode(this);
    }
}
