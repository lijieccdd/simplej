package com.simplejframework.helper;

import com.simplejframework.utils.ClassUtil;

/**
 * Created by dell on 2017/12/26.
 */
public class LoaderHelper {
    public static void init(){
        ClassUtil.loadClass(ClassHelper.class.getName(),true);
        ClassUtil.loadClass(BeanHelper.class.getName(),true);
        ClassUtil.loadClass(IocHelper.class.getName(),true);
        ClassUtil.loadClass(ControllerHelper.class.getName(),true);
    }
}
