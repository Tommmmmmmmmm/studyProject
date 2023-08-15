package com.qyy.springboot.dao;

import com.qyy.springboot.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Qyy
 * @date 2023/8/15 09:55
 */
@Repository
public interface UserMapper {
    List<User> queryList();
}
