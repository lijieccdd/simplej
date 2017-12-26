package com.simplejframework;

import com.alibaba.druid.support.json.JSONUtils;
import com.simplejframework.bean.Data;
import com.simplejframework.bean.Handler;
import com.simplejframework.bean.View;
import com.simplejframework.constant.ConfigConstant;
import com.simplejframework.helper.BeanHelper;
import com.simplejframework.helper.ConfigHelper;
import com.simplejframework.helper.ControllerHelper;
import com.simplejframework.helper.LoaderHelper;
import com.simplejframework.utils.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * mvc核心转发器
 * Created by dell on 2017/12/25.
 */
@WebServlet(value = "/*",loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet{
    @Override
    public void init(ServletConfig config) throws ServletException {
        LoaderHelper.init();
        //注册jsp、静态资源处理器
        ServletContext servletContext = config.getServletContext();
        ServletRegistration jspServletRegistration = servletContext.getServletRegistration("jsp");
        jspServletRegistration.addMapping(ConfigHelper.getValue(ConfigConstant.prefixJsp)+"*");

        ServletRegistration defaultServletRegistration = servletContext.getServletRegistration("default");
        defaultServletRegistration.addMapping(ConfigHelper.getValue(ConfigConstant.staticResource)+"*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从请求中得到url
        String requestUrl = req.getPathInfo();
        //根据url得到handler
        Handler handler = ControllerHelper.getHandler(requestUrl);

        if (handler==null){
            throw new RuntimeException("can't find handler by url:"+requestUrl);
        }
        //获取controller类及其实例
        Class controllerClass = handler.getController();
        Object controllerIns = BeanHelper.getBean(controllerClass);
        //得到处理请求的方法
        Method actionMethod = handler.getMethod();
        //从请求中获得请求参数
        Map<String, Object> paramMap = getParamMap(req);
        //通过反射调用handler相应的方法
        Object result = ReflectionUtil.invokMethod(controllerIns,actionMethod,paramMap);

        //根据返回结果，响应客户端
        respClientByResult(result,req,resp);
    }

    private void respClientByResult(Object result,HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (result==null){
            throw new RuntimeException("invok method retrun null");
        }
        if (result instanceof View){
            View view = (View) result;
            String jspPath = view.getJspPath();
            Map<String,Object> data =  view.getData();

            if (StringUtils.isEmpty(jspPath)){
                throw new RuntimeException("return jsp path can't be empty");
            }
            if (jspPath.startsWith("redirect:")){
                resp.sendRedirect(req.getContextPath()+jspPath);
            }else{
                if (MapUtils.isNotEmpty(data)){
                    for (Map.Entry<String, Object> dataEntry : data.entrySet()) {
                        String key = dataEntry.getKey();
                        Object value = dataEntry.getValue();
                        req.setAttribute(key,value);
                    }
                }
                req.getRequestDispatcher(ConfigHelper.getValue(ConfigConstant.prefixJsp)+jspPath).forward(req,resp);
            }
        }else if (result instanceof Data){
            Data data = (Data) result;
            Object model = data.getModel();
            if (model==null){
                throw new RuntimeException("return data can't be null");
            }
            String json = JSONUtils.toJSONString(model);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    private Map<String, Object> getParamMap(HttpServletRequest req){
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<String> reqParameters = req.getParameterNames();
        while (reqParameters.hasMoreElements()){
            String paramName = reqParameters.nextElement();
            String paramValue = req.getParameter(paramName);
            paramMap.put(paramName,paramValue);
        }
        return paramMap;
    }
}
