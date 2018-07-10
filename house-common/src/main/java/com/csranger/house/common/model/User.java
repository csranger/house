package com.csranger.house.common.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class User {
    private long id;           //表单无  自动生成
    private String name;       //表单有
    private String phone;     //表单有
    private String email;     //表单有
    private String aboutme;   //表单有
    private String passwd;     //表单有
    private String avatar;       // 表单无 url 地址，头像图片
    private String type;         // 表单有 用户类型 1-普通用户 2-经纪人
    private Date createTime;      // 表单无
    private Integer enable;      // 是否激活 1-启用 0-未激活
    private Long agencyId;       // 表单无 经纪机构 id
    // 以下是数据库没有的字段，但是是User类的属性，它们出现在 /accounts/register 页面表单里
    private String confirmPasswd;            // 用于判断输入的密码是否出错，判断表单信息是否有误
    private MultipartFile avatarFile;        // 表单有，图像文件，生成存储文件相对地址 avatar
    private String newPassword;              // 用于修改密码，修改 passwd
    // 以下属性数据库和表单均没有
    private String key;                      // 激活连接里的key，用于计划用户

    //getter setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getConfirmPasswd() {
        return confirmPasswd;
    }

    public void setConfirmPasswd(String confirmPasswd) {
        this.confirmPasswd = confirmPasswd;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
