package com.qyy.springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qyy.springboot.dao.UserMapper;
import com.qyy.springboot.entity.User;
import com.qyy.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Qyy
 * @date 2023/8/15 10:00
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<User> getUserList() {
        String key="userList";
        List<User> userList = (List<User>)redisTemplate.opsForValue().get(key);
        if (userList==null){
            userList=userMapper.queryList();
            if (userList!=null){
                redisTemplate.opsForValue().set(key,userList,1000, TimeUnit.MINUTES);
            }
        }
        return userList;
    }
}
