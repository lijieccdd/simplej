package com.simplejframework.helper;

import com.simplejframework.annotation.Controller;
import com.simplejframework.annotation.Service;
import com.simplejframework.constant.ConfigConstant;
import com.simplejframework.utils.ClassUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dell on 2017/12/26.
 */
public class ClassHelper {
    private static Logger logger = LoggerFactory.getLogger(ClassHelper.class);
    private static Set<Class> classSet = new HashSet<>();

    static {
        String basePackage = ConfigHelper.getValue(ConfigConstant.baseScanPackage);
        logger.debug("start scan basePackage,basePackage:"+basePackage);
        if (StringUtils.isEmpty(basePackage)){
            throw new RuntimeException("properties **simplej.basescan.package** can't be empty");
        }
        classSet = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class> getAllClass(){
        return classSet;
    }

    public static Set<Class> getServiceClass(){
        Set<Class> serviceClassSet = new HashSet<>();
        for (Class aClass : classSet) {
            if (aClass.isAnnotationPresent(Service.class)){
                serviceClassSet.add(aClass);
            }
        }
        return serviceClassSet;
    }

    public static Set<Class> getControllerClass(){
        Set<Class> serviceClassSet = new HashSet<>();
        for (Class aClass : classSet) {
            if (aClass.isAnnotationPresent(Controller.class)){
                serviceClassSet.add(aClass);
            }
        }
        return serviceClassSet;
    }

    public static Set<Class> getAllBeanClass(){
        Set<Class> beanClass = new HashSet<>();
        beanClass.addAll(getServiceClass());
        beanClass.addAll(getControllerClass());
        return beanClass;
    }
}
