package com.alice.springbootmall.dao;

import com.alice.springbootmall.dto.UserRegisterRequest;
import com.alice.springbootmall.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);


}
