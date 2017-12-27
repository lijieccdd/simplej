package com.simplejdemo.util;

import java.util.Properties;

/**
 * 读取属性配置文件
 * Created by dell on 2017/12/23.
 */
public class ProsUtils {

    /**
     * 读取属性配置文件
     * @param fileName
     * @return
     */
    public static Properties loadProperties(String fileName){
        return null;
    }

    /**
     * 通过键 得到值
     * @param properties
     * @param key
     * @return
     */
    public static String getValue(Properties properties,String key){
        return  getValue(properties,key,"");
    }

    /**
     * 通过键得到值（可指定默认值）
     * @param properties
     * @param key
     * @param defaultVal
     * @return
     */
    public static String getValue(Properties properties,String key,String defaultVal){
        String value = defaultVal;
        if(properties!=null&&properties.contains(key)){
            value = properties.getProperty(key);
        }
        return  value;
    }
}
