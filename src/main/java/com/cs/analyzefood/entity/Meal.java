package com.cs.analyzefood.entity;

import java.util.Date;

public class Meal {
    private int mealId;
    private int roleId;
    private String mealName;
    private double targetEnergy;
    private String describe;
    private Date createTime;
    private double per_carbohydrate;
    private double per_protein;
    private double per_fat;
    private double per_zao;
    private double per_zhong;
    private double per_wan;
    private double dayEnergy;
    private double dayCHO;
    private double dayProtein;
    private double dayFat;

    public Meal() {
    }

    public Meal(int roleId, String mealName, double targetEnergy, String describe, Date createTime, double per_carbohydrate, double per_protein, double per_fat, double per_zao, double per_zhong, double per_wan, double dayEnergy, double dayCHO, double dayProtein, double dayFat) {
        this.roleId = roleId;
        this.mealName = mealName;
        this.targetEnergy = targetEnergy;
        this.describe = describe;
        this.createTime = createTime;
        this.per_carbohydrate = per_carbohydrate;
        this.per_protein = per_protein;
        this.per_fat = per_fat;
        this.per_zao = per_zao;
        this.per_zhong = per_zhong;
        this.per_wan = per_wan;
        this.dayEnergy = dayEnergy;
        this.dayCHO = dayCHO;
        this.dayProtein = dayProtein;
        this.dayFat = dayFat;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public double getTargetEnergy() {
        return targetEnergy;
    }

    public void setTargetEnergy(double targetEnergy) {
        this.targetEnergy = targetEnergy;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getPer_carbohydrate() {
        return per_carbohydrate;
    }

    public void setPer_carbohydrate(double per_carbohydrate) {
        this.per_carbohydrate = per_carbohydrate;
    }

    public double getPer_protein() {
        return per_protein;
    }

    public void setPer_protein(double per_protein) {
        this.per_protein = per_protein;
    }

    public double getPer_fat() {
        return per_fat;
    }

    public void setPer_fat(double per_fat) {
        this.per_fat = per_fat;
    }

    public double getPer_zao() {
        return per_zao;
    }

    public void setPer_zao(double per_zao) {
        this.per_zao = per_zao;
    }

    public double getPer_zhong() {
        return per_zhong;
    }

    public void setPer_zhong(double per_zhong) {
        this.per_zhong = per_zhong;
    }

    public double getPer_wan() {
        return per_wan;
    }

    public void setPer_wan(double per_wan) {
        this.per_wan = per_wan;
    }

    public double getDayEnergy() {
        return dayEnergy;
    }

    public void setDayEnergy(double dayEnergy) {
        this.dayEnergy = dayEnergy;
    }

    public double getDayCHO() {
        return dayCHO;
    }

    public void setDayCHO(double dayCHO) {
        this.dayCHO = dayCHO;
    }

    public double getDayProtein() {
        return dayProtein;
    }

    public void setDayProtein(double dayProtein) {
        this.dayProtein = dayProtein;
    }

    public double getDayFat() {
        return dayFat;
    }

    public void setDayFat(double dayFat) {
        this.dayFat = dayFat;
    }
}
