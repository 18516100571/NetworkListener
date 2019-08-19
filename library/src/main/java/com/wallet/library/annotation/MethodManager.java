package com.wallet.library.annotation;

import com.wallet.library.type.NetType;

import java.lang.reflect.Method;

public class MethodManager {
    //要执行的方法
    private  Method method;
    //参数类型
    private Class<?> type;

    //网络类型
    private NetType netType;

    public MethodManager(Method method, Class<?> type, NetType netType) {
        this.method = method;
        this.type = type;
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }
}
