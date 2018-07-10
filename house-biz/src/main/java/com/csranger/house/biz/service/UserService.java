package com.csranger.house.biz.service;

import com.csranger.house.common.model.User;
import com.csranger.house.biz.dao.UserDao;
import com.csranger.house.common.utils.BeanHelper;
import com.csranger.house.common.utils.HashUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;


    /**
     * @return
     */
    public List<User> getUsers() {
        return userDao.selectUsers();
    }


    /**
     * 用户在注册页面填写"合适"表单后，在数据库中插入记录，然后向用户发送邮件
     * 对此方法进行事务管理，注意两点：1。默认RuntimeException(非检查性异常)才会回滚，这里设为Exception则检查性异常也会回滚；
     * 2。这个被事务注解的方法需要在其他类中调用才行，如果在UserService类中其他方法调用事务是不会生效的
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        // 1. 将 account 的密码加密后设为 account 的密码
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        // 2. 将 account 的上传的图像 avatarFile 转化成存储在本地静态资源地方的路径设为 account 的 avatar
        List<String> imgList = fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
        if (!imgList.isEmpty()) {
            account.setAvatar(imgList.get(0));  // 因为只提交一个图片作为头像
        }
        // BeanHelper作用可将默认值填充进去
        // 3. 设置 account 对象属性默认值   setDefaultProp 方法根据不同属性的类型进行反射调用，将默认值设置进去
        // account 对象是 User 类型，而表单指制定了大部分属性的值，其他属性设为默认值，然后通过4，5，6在修改部分默认值
        BeanHelper.setDefaultProp(account, User.class);
        // 4. 设置 account 对象 updateTime createTime 属性值为当前时间
        BeanHelper.onInsert(account);
        // 5. 设置 account 对象 enable 属性，表示用户未激活
        account.setEnable(0);
        // 6. 使用 account 对象向数据库 user 表中插入一行数据
        userDao.insert(account);
        // 7. 向用户发送验证邮件
        mailService.registerNotify(account.getEmail());
        return true;
    }


    public boolean enable(String key) {
        return mailService.enable(key);
    }

}
