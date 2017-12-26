package com.simplejframework.bean;

import java.lang.reflect.Method;

/**
 * 处理器
 * Created by dell on 2017/12/26.
 */
public class Handler {
    private Class controller;
    private Method method;

    public Handler(Class controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Class getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }
}
