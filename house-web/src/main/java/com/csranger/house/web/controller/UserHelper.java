package com.csranger.house.web.controller;

import com.csranger.house.common.model.User;
import com.csranger.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具类，判断用户填写的表单是否"正确"：有没有天email，name，密码是否前后输入不一致，密码是否长短很短
 */
public class UserHelper {

    // 将表单参数传入这个工具类会生成一个 ResultMsg 对象，根据对象的 isSuccess 方法判断表单是否可行
    public static ResultMsg validate(User account) {
        // 如果填写的表单(注册信息)有问题，将错误信息写入结果的属性
        // 如果填写的表单(注册信息)无问题，错误信息为空，使用successMsg静态方法创建对象
        if (StringUtils.isBlank(account.getEmail())) {
            return ResultMsg.errorMsg("Email 有误");
        }
        if (StringUtils.isBlank(account.getName())) {
            return ResultMsg.errorMsg("Name 有误");
        }
        if (StringUtils.isBlank(account.getPasswd()) || StringUtils.isBlank(account.getPasswd()) ||
                !account.getPasswd().equals(account.getConfirmPasswd())) {
            return ResultMsg.errorMsg("密码有误");
        }
        if (account.getPasswd().length() < 6) {
            return ResultMsg.errorMsg("密码需大于6位");
        }
        return ResultMsg.successMsg("");
    }
}
