package com.simplejframework.helper;

import com.simplejframework.annotation.RequestMapping;
import com.simplejframework.bean.Handler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dell on 2017/12/26.
 */
public class ControllerHelper {
    //请求url与处理器map
    private static Map<String,Handler> actionMap = new HashMap<>();

    static {
        //得到所有controller class set
        Set<Class> classSet = ClassHelper.getControllerClass();

        //遍历controller class set
        if (CollectionUtils.isNotEmpty(classSet)){
            for (Class controllerClass : classSet) {
                //遍历每个controller的方法，如果有requestmaping注解，将其放到actionmap中
                Method[] methods = controllerClass.getDeclaredMethods();

                if (ArrayUtils.isNotEmpty(methods)){
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)){
                            RequestMapping requestMapping =  method.getAnnotation(RequestMapping.class);
                            String requestUrl = requestMapping.value();
                            actionMap.put(requestUrl,new Handler(controllerClass,method));
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestUrl){
        return actionMap.get(requestUrl);
    }
}
