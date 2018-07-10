package com.csranger.house.biz.service;

import com.csranger.house.biz.dao.UserDao;
import com.csranger.house.common.model.User;
import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MailService {


    @Autowired
    private UserDao userDao;

    // 本地缓存:maximumSize(100)假设了同时进行邮件注册的不超过100个
    // 本地缓存最常用的使用 Guava Cache
    private final Cache<String, String> registerCache = CacheBuilder.newBuilder()    // 使用 CacheBuilder 构建本地缓存
            .maximumSize(100)                                                         // 最大存储空间
            .expireAfterAccess(15, TimeUnit.MINUTES)                          // 过期时间
            .removalListener(new RemovalListener<String, String>() {

                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {   // notification 就是存储的 k-v
                    String email = notification.getValue();
                    User user = new User();
                    user.setEmail(email);
                    List<User> targetUser = userDao.selectUsersByQuery(user);
                    if (!targetUser.isEmpty() && Objects.equal(targetUser.get(0).getEnable(), 0)) {
                        userDao.delete(email);// 代码优化: 在删除前首先判断用户是否已经被激活，对于未激活的用户进行移除操作
                    }
//                    userDao.delete(notification.getValue());
                }
            }).build();

    // spring mail 起步依赖里已经创建的Bean
    @Autowired
    private JavaMailSender mailSender;


    // 发送验证信息的邮件：csranger@163.com
    @Value("${spring.mail.userName}")
    private String from;

    // 127.0.0.1:8090
    @Value("${domain.name}")
    private String domainName;

    /**
     * 从 from (csranger@163.com) 向注册用户的 email 发送邮件
     * @param title  邮件标题
     * @param url    邮件内容，即激活连接
     * @param email  邮件地址，发送的地方
     */
    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText(url);
        mailSender.send(message);
    }




    /**
     * 根据注册页面用户填写的表单的 email 发送激活连接 url；所以 url 是邮件内容，randomKey 是注册 email 的生成的用于激活账号的 key
     * 1.缓存key-email的关系
     * 2.借助spring-email发送邮件
     * 3.借助异步框架进行异步操作
     * 注意三点：(1)。这个注解会使得加载这个方法会调用一个线程池去执行；(2)。开启异步框架需要在启动类上添加@EnableAsync
     * (3)。调用方所在的类和方法定义的类需要在不同的类中，这里 registerNotify 方法不可以放在 UserService 类中
     *
     * @param email
     */
    @Async
    public void registerNotify(String email) {
        // 随机生成10位的字符串，放在缓存里
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey, email);
        String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
        sendMail("房产平台激活邮件", url, email);
    }

    public boolean enable(String key) {
        // 1. 如果 key 对应的 mail 不在缓存中，意味着注册失效
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        // 2. 如果 key 对应的 mail 在缓存中，对此用户进行数据库更新操作
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        userDao.update(updateUser);
        registerCache.invalidate(key);    // 将 key 从 map 中删除
        return true;
    }
}
