package com.wallet.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.wallet.library.annotation.NetworkManager;
import com.wallet.library.type.NetType;

public class NetWorkUtils {
    /**
     * 检查网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }

    /**
     *  * 判断WIFI网络是否可用
     *  * @param context
     *  * @return
     *  
     */


    public static boolean isWifiConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     *  * 判断MOBILE网络是否可用
     *  * @param context
     *  * @return
     *  
     */


    public static boolean isMobileConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mMobileNetworkInfo != null) {
            return mMobileNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     *  * 获取当前网络连接的类型信息
     *  * @param context
     *  * @return
     *  
     */


    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     *  * 获取当前的网络状态 ：没有网络0：WIFI网络1：3G网络2：2G网络3
     *  *
     *  * @param context
     *  * @return
     *  
     */


    public static NetType getAPNType() {
        int netType = 0;
        ConnectivityManager connMgr = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
//            int nSubType = networkInfo.getSubtype();
//            TelephonyManager mTelephony = (TelephonyManager) NetworkManager.getDefault().getApplication()
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
//                    && !mTelephony.isNetworkRoaming()) {
//                netType = 2;// 3G
//            } else {
//                netType = 3;// 2G
//            }
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {

                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }


        }
        return NetType.NONE;
    }

}
