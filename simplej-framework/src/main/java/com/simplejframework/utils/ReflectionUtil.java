package com.simplejframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Created by dell on 2017/12/25.
 */
public class ReflectionUtil {
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     * @param cls
     * @return
     */
    public static Object newInstance(Class cls){
        Object obj = null;
        try {
            obj = cls.newInstance();
        } catch (Exception e) {
            logger.error("instance class failed:"+cls);
        }
        return obj;
    }

    /**
     * 调用方法
     * @param object
     * @param method
     * @param args
     * @return
     */
    public static Object invokMethod(Object object, Method method,Object...args){
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(object,args);
        } catch (Exception e) {
            logger.error("instance method failed",e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     */
    public static void setFiled(Object obj, Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            logger.error("set field failed",e);
        }
    }
}
