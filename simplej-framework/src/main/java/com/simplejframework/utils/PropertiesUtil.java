package com.simplejframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取属性配置文件工具类
 * Created by dell on 2017/12/25.
 */
public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 读取属性配置文件
     * @param fileName
     * @return
     */
    public static Properties loadProperties(String fileName){
        Properties properties = null;
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            properties = new Properties();
            properties.load(is);
        } catch (Exception e) {
            logger.error("load properties error",e);
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("close InputStream error",e);
                }
            }
        }
        logger.debug("load properties:"+fileName+",success");
        return properties;
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
        if(properties!=null&&properties.containsKey(key)){
            value = properties.getProperty(key);
        }
        return  value;
    }
}
