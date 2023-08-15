package com.qyy.springboot.service.impl;

import com.qyy.springboot.dao.UserMapper;
import com.qyy.springboot.entity.User;
import com.qyy.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Qyy
 * @date 2023/8/15 10:00
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getUserList() {
        return userMapper.queryList();
    }
}
