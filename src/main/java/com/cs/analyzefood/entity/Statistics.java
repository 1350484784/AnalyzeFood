package com.cs.analyzefood.entity;

import java.util.Date;

public class Statistics {
    private int id;
    private int roleId;
    private double gu;
    private double dou;
    private double shu;
    private double guo;
    private double rou;
    private double nai;
    private double dan;
    private double yu;
    private double you;
    private Date createTime;

    public Statistics() {
    }

    public Statistics(int roleId, double gu, double dou, double shu, double guo, double rou, double nai, double dan, double yu, double you, Date createTime) {
        this.roleId = roleId;
        this.gu = gu;
        this.dou = dou;
        this.shu = shu;
        this.guo = guo;
        this.rou = rou;
        this.nai = nai;
        this.dan = dan;
        this.yu = yu;
        this.you = you;
        this.createTime = createTime;
    }

    public Statistics(int id, int roleId, double gu, double dou, double shu, double guo, double rou, double nai, double dan, double yu, double you, Date createTime) {
        this.id = id;
        this.roleId = roleId;
        this.gu = gu;
        this.dou = dou;
        this.shu = shu;
        this.guo = guo;
        this.rou = rou;
        this.nai = nai;
        this.dan = dan;
        this.yu = yu;
        this.you = you;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public double getGu() {
        return gu;
    }

    public void setGu(double gu) {
        this.gu = gu;
    }

    public double getDou() {
        return dou;
    }

    public void setDou(double dou) {
        this.dou = dou;
    }

    public double getShu() {
        return shu;
    }

    public void setShu(double shu) {
        this.shu = shu;
    }

    public double getGuo() {
        return guo;
    }

    public void setGuo(double guo) {
        this.guo = guo;
    }

    public double getRou() {
        return rou;
    }

    public void setRou(double rou) {
        this.rou = rou;
    }

    public double getNai() {
        return nai;
    }

    public void setNai(double nai) {
        this.nai = nai;
    }

    public double getDan() {
        return dan;
    }

    public void setDan(double dan) {
        this.dan = dan;
    }

    public double getYu() {
        return yu;
    }

    public void setYu(double yu) {
        this.yu = yu;
    }

    public double getYou() {
        return you;
    }

    public void setYou(double you) {
        this.you = you;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
