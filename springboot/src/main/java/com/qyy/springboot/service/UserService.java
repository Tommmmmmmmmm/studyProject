package com.qyy.springboot.service;

import com.qyy.springboot.entity.User;

import java.util.List;

/**
 * @author Qyy
 * @date 2023/8/15 09:59
 */
public interface UserService {
    List<User> getUserList();
}
