package com.qyy.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qyy.springboot.entity.User;
import com.qyy.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Qyy
 * @date 2023/8/15 09:52
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/getList")
    public String getUserList(){
        List<User> userList = userService.getUserList();
        ObjectMapper objectMapper = new ObjectMapper();
        String json=null;
        try {
             json = objectMapper.writeValueAsString(userList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
