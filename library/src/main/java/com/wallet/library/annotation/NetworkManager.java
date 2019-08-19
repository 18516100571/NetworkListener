package com.wallet.library.annotation;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;
import com.wallet.library.Constants;
import com.wallet.library.NetStateReceive;
import com.wallet.library.NetworkCallback;

public class NetworkManager {
    private static volatile NetworkManager instance;
    private Application application;
    private NetStateReceive receive;
    private NetworkCallback networkCallback;

    public NetworkManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback = new NetworkCallback();
        } else {
            receive = new NetStateReceive();
        }
    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }


        }
        return instance;


    }

    public Application getApplication() {
        if (application == null) {
            throw new RuntimeException("application not null");
        }
        return application;
    }

    public void init(Application application) {
        this.application = application;
        //动态注册广播
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            NetworkCallback networkCallback = new NetworkCallback();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            if (networkCallback == null) {
                throw new NullPointerException("networkCallback == null");
            }
            ConnectivityManager connMgr =
                    (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr != null) {
                connMgr.registerNetworkCallback(request, networkCallback);
            }

        } else {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
            application.registerReceiver(receive, intentFilter);


        }

    }

    public void register(Object object) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (networkCallback == null) {
                throw new NullPointerException("networkCallback == null");
            }
            networkCallback.register(object);

        } else {
            if (receive == null) {
                throw new NullPointerException("receive == null");
            }

            receive.register(object);

        }
    }

    public void unRegister(Object object) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (networkCallback == null) {
                throw new NullPointerException("networkCallback == null");
            }
            networkCallback.unRegister(object);

        } else {
            if (receive == null) {
                throw new NullPointerException("receive == null");
            }

            receive.unRegister(object);

        }

    }
}
