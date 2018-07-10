package com.csranger.house.web.controller;

import com.csranger.house.biz.service.UserService;
import com.csranger.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "hello")
    public String hello(Model model) {
        List<User> users = userService.getUsers();
        User one = users.get(0);
        if (one != null) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("user", one);
        return "hello";
    }

    @RequestMapping(value = "index")
    public String index() {
        return "homepage/index";
    }


}
