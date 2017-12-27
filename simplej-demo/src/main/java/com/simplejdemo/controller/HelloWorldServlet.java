package com.simplejdemo.controller;

import com.simplejdemo.service.CustomerService;
import com.simplejframework.annotation.Autowrite;
import com.simplejframework.annotation.Controller;
import com.simplejframework.annotation.RequestMapping;
import com.simplejframework.bean.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by dell on 2017/12/23.
 */
@Controller
public class HelloWorldServlet extends HttpServlet{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowrite
    private CustomerService customerService;

    @RequestMapping(value = "/index")
    public View hello(Map<String,Object> paramMap){
        logger.info(customerService.getCustomerList());
        View view = new View();
        view.setJspPath("index.jsp");
        return view;
    }
}
