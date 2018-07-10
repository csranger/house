package com.csranger.house.web.controller;

import com.csranger.house.common.model.User;
import com.csranger.house.biz.service.UserService;
import com.csranger.house.common.result.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 查询全部用户
     * @return
     */
    @RequestMapping(value = "user")
    @ResponseBody
    public List<User> getUsers() {
        return userService.getUsers();
    }


    /**
     * 注册页面提交POST：就是填写注册表单信息后提交到这个页面 1. 注册验证 2. 发送邮件 3. 验证失败重定向到注册页面
     * 注册页面获取GET：获取表单页面以填写注册信息
     *
     * 在浏览器输入 /accounts/register Get请求，返回 /user/accounts/register 模版页面填写表单，填写后提交即 /accounts/register POST请求；
     * 验证成功，添加用户(addAccount)：在数据库中插入一条记录，且是非激活状态的；key和email绑定，之后发邮件让用户点击激活，绑定后发送一条含有key的邮件给用户
     * 邮箱；使用邮箱的链接 /accounts/register Get请求激活用户，返回
     * @param account    表单
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/accounts/register")
    public String accountsRegister(User account, ModelMap modelMap) {
        // 1. 如果是 Get 请求，则不是在注册页面发送注册信息表单，而是直接请求注册页面
        if (account == null || account.getName() == null) {
            return "/user/accounts/register";
        }

        // 2. 如果是 Post 请求，即发送注册表单数据；则进行注册信息检查:姓名或密码是否为空；密码长度是否很短；密码与确认密码是否相同等等；
        ResultMsg resultMsg = UserHelper.validate(account);
        // 2.1 如果注册信息没问题(isSuccess)，添加用户(addAccount)：在数据库中插入一条记录，且是非激活状态的；key和email绑定，之后发邮件让用户点击激活
        if (resultMsg.isSuccess() && userService.addAccount(account)) {
            modelMap.put("email", account.getEmail());
            return "/user/accounts/registerSubmit";
        // 2.2 如果注册信息有问题(密码为很短，为填写邮件等等)会将错误信息放入 url 中即 asUrlParams
        } else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping(value = "/accounts/verify")
    public String verify(String key) {
        boolean result = userService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败，请确认激活连接是否过期？").asUrlParams();
        }

    }
}
