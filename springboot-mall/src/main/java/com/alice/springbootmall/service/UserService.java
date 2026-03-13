package com.alice.springbootmall.service;

import com.alice.springbootmall.dto.UserRegisterRequest;
import com.alice.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);


}
