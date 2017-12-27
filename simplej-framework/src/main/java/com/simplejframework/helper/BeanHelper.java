package com.simplejframework.helper;

import com.simplejframework.constant.ConfigConstant;
import com.simplejframework.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dell on 2017/12/25.
 */
public final class BeanHelper {
    /**
     * 存放bean类与bean实例的关系
     */
    private static final Map<Class<?>,Object> beanMap = new HashMap<>();

    static {
        //通过类加载助手类得到，得到基础包下所有class
        Set<Class> classSet = ClassHelper.getAllBeanClass();
        //通过反射实例化类
        for (Class aClass : classSet) {
            Object obj = ReflectionUtil.newInstance(aClass);
            beanMap.put(aClass,obj);
        }
    }

    /**
     * 得到beanmap
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return beanMap;
    }

    public static <T> T getBean(Class<T> tClass){
        T t = null;
        if (beanMap.containsKey(tClass)){
            t = (T) beanMap.get(tClass);
        }
        return t;
    }
}
