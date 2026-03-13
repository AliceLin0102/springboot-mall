package com.alice.springbootmall.service.impl;

import com.alice.springbootmall.dao.UserDao;
import com.alice.springbootmall.dto.UserRegisterRequest;
import com.alice.springbootmall.model.User;
import com.alice.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
