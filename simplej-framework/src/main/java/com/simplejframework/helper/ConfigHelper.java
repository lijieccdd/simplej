package com.simplejframework.helper;

import com.simplejframework.constant.ConfigConstant;
import com.simplejframework.utils.PropertiesUtil;

import java.util.Properties;

/**
 * 属性文件助手类
 * Created by dell on 2017/12/25.
 */
public class ConfigHelper {
    private static final Properties confProperties = PropertiesUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    public static String getValue(String key){
        return PropertiesUtil.getValue(confProperties,key);
    }
}
