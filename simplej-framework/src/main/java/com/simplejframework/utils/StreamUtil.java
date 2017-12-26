package com.simplejframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具类
 * Created by dell on 2017/12/26.
 */
public class StreamUtil {
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public static String getString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line=br.readLine())!=null){
            sb.append(line);
        }
        return sb.toString();
    }
}
