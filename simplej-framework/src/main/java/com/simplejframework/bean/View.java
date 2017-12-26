package com.simplejframework.bean;

import java.util.Map;

/**
 * 返回视图对象
 * Created by dell on 2017/12/26.
 */
public class View {
    private String jspPath;
    private Map<String, Object> data;

    public String getJspPath() {
        return jspPath;
    }

    public void setJspPath(String jspPath) {
        this.jspPath = jspPath;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
