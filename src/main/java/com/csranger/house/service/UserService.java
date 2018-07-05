package com.csranger.house.service;

import com.csranger.house.common.model.User;
import com.csranger.house.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public List<User> getUsers() {
        return userMapper.selectUsers();
    }
}
