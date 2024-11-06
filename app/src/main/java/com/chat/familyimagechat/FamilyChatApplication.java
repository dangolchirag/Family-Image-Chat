package com.chat.familyimagechat;

import android.app.Application;
import android.widget.Toast;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class FamilyChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
    }
}
