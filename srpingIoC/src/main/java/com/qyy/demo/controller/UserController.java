package com.qyy.demo.controller;

import com.qyy.demo.service.UserService;
import com.qyy.mvcframework.annotation.QYYAutoWried;
import com.qyy.mvcframework.annotation.QYYController;
import com.qyy.mvcframework.annotation.QYYRequestMapping;
import com.qyy.mvcframework.annotation.QYYRequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Qyy
 * @date 2023/8/9 18:09
 */
@QYYRequestMapping("/user")
@QYYController
public class UserController {
    @QYYAutoWried
    private UserService userService;

    @QYYRequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp, @QYYRequestParam("name")String name){
        String result = "My name is " + name;
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
