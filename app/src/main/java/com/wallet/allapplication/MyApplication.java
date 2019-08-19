package com.wallet.allapplication;

import android.app.Application;
import com.wallet.library.annotation.NetworkManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getDefault().init(this);

    }
}
