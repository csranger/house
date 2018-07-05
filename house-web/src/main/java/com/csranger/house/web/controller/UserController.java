package com.csranger.house.web.controller;

import com.csranger.house.common.model.User;
import com.csranger.house.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     *
     * @return
     */
    @RequestMapping(value = "user")
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
