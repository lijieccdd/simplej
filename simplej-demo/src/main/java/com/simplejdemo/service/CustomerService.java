package com.simplejdemo.service;

import com.simplejframework.annotation.Service;

/**
 * Created by dell on 2017/12/23.
 */
@Service
public class CustomerService {
    public String getCustomerList(){
        return "customer";
    }
}
