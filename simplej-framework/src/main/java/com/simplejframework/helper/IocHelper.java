package com.simplejframework.helper;

import com.simplejframework.annotation.Autowrite;
import com.simplejframework.utils.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by dell on 2017/12/26.
 */
public class IocHelper {
    /**
     * 遍历beanmap,得到标注了autowrite注解的字段进行依赖注入
     */
    static {
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if (!MapUtils.isEmpty(beanMap)){
            for (Map.Entry<Class<?>, Object> classObjectEntry : beanMap.entrySet()) {
                Class beanCls = classObjectEntry.getKey();
                Object beanInstance = classObjectEntry.getValue();

                Field[] fields = beanCls.getFields();
                if(!ArrayUtils.isEmpty(fields)){
                    for (Field beanField : fields) {
                        if (beanField.isAnnotationPresent(Autowrite.class)){
                            Class beanFieldType = beanField.getType();
                            Object beanFieldValue = BeanHelper.getBean(beanFieldType);
                            ReflectionUtil.setFiled(beanInstance,beanField,beanFieldValue);
                        }
                    }
                }
            }
        }
    }
}
