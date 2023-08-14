package com.qyy.demo.service.impl;

import com.qyy.demo.service.UserService;
import com.qyy.mvcframework.annotation.QYYService;

/**
 * @author Qyy
 * @date 2023/8/9 18:11
 */
@QYYService
public class UserServiceImpl implements UserService {
    @Override
    public String get(String name) {
        return "My name is " + name + ",from service.";
    }
}
