package com.cs.analyzefood.entity;

import java.util.Date;

public class User {
    private int roleId;
    private String roleAccount;
    private String name;
    private String password;
    private int age;
    private int weight;
    private String sex;
    private String phone;
    private String headImg;
    private Date createTime;
    private byte onlineFlag;

    public User() {
    }

    public User(String roleAccount, String password, String phone, String headImg, Date createTime) {
        this.roleAccount = roleAccount;
        this.password = password;
        this.phone = phone;
        this.headImg = headImg;
        this.createTime = createTime;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleAccount() {
        return roleAccount;
    }

    public void setRoleAccount(String roleAccount) {
        this.roleAccount = roleAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public byte getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(byte onlineFlag) {
        this.onlineFlag = onlineFlag;
    }
}
