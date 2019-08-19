package com.wallet.library;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.wallet.library.annotation.MethodManager;
import com.wallet.library.type.NetType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.wallet.library.Constants.LOG_TAG;

public class NetworkCallback extends ConnectivityManager.NetworkCallback {

    private NetType netType;
    private Map<Object, List<MethodManager>> networkList;

    public NetworkCallback() {
        this.netType = NetType.NONE;
        networkList = new HashMap<>();
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        Log.e(LOG_TAG, "callBack网络连接");
        netType = NetType.AUTO;
        post(netType);


    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        Log.e(LOG_TAG, "callBack网络断开");
        netType = NetType.NONE;
        post(netType);


    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        Log.e(LOG_TAG, "callBack网络更换");

        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.e(LOG_TAG, "callBack类型为wifi");
            } else if (networkCapabilities.hasTransport(networkCapabilities.TRANSPORT_ETHERNET)) {
                Log.e(LOG_TAG, "callBack类型为以太网络");
            }
            netType = NetWorkUtils.getAPNType();
            post(netType);

        }


    }

    private void post(NetType netType) {

        if (networkList.isEmpty()) {
            return;
        }
        Set<Object> set = networkList.keySet();
        for (Object getter : set) {
            //得到某一个actiity 或者fragment 重的所有订阅方法
            List<MethodManager> methodManagers = networkList.get(getter);
            for (MethodManager methodManager : methodManagers) {
                //参数的匹配。
                if (methodManager.getType().isAssignableFrom(netType.getClass())) {

                    switch (methodManager.getNetType()) {
                        case AUTO:
                            invoke(getter, methodManager, netType);
                            break;
//                        case NONE:
//                            invoke(getter, methodManager, netType);
//                            break;
                        case WIFI:
                            if (NetType.WIFI == netType || NetType.NONE == netType) {
                                invoke(getter, methodManager, netType);
                            }

                            break;
                        case CMNET:
                        case CMWAP:
                            if (NetType.CMNET == netType || NetType.CMWAP == netType || NetType.NONE == netType) {
                                invoke(getter, methodManager, netType);
                            }
//                            invoke(getter, methodManager, netType);

                            break;
                    }


                }


            }


        }


    }

    private void invoke(Object getter, MethodManager methodManager, NetType netType) {
        try {
            methodManager.getMethod().invoke(getter, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    public void register(Object object) {
        List<MethodManager> methodList = networkList.get(object);
        if (methodList == null) {
            List<MethodManager> networkAnnotationMethods = findNetworkAnnotationMethods(object);
            networkList.put(object, networkAnnotationMethods);
        }
    }

    private List<MethodManager> findNetworkAnnotationMethods(Object object) {
        List<MethodManager> methodList = new ArrayList<>();

        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
//订阅方法的收集
        for (Method method : methods) {
         com.wallet.library.annotation.Network network = method.getAnnotation(com.wallet.library.annotation.Network.class);
            if (network == null) {
                continue;
            }
            //获取方法的返回值
//            Type genericReturnType = method.getGenericReturnType();
//            if (genericReturnType.getTypeName().equals("void")) {
//                continue;
//            }
            //获取方法的参数
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException(method.getName() + "有且只有一个参数");
            }
            MethodManager methodManager = new MethodManager(method, parameterTypes[0], network.netType());
            methodList.add(methodManager);

        }

        return methodList;
    }


    public void unRegister(Object object) {
        if (networkList.get(object) != null) {
            networkList.remove(object);
        }
    }

}
