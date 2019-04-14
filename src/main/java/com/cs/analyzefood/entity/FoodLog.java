package com.cs.analyzefood.entity;

import java.util.Date;

public class FoodLog {
    private int id;
    private int roleId;
    private int foodId;
    private int foodNum;
    private double weight;
    private Date createTime;

    public FoodLog() {
    }

    public FoodLog(int roleId, int foodId, int foodNum, double weight, Date createTime) {
        this.roleId = roleId;
        this.foodId = foodId;
        this.foodNum = foodNum;
        this.weight = weight;
        this.createTime = createTime;
    }

    public FoodLog(int id, int roleId, int foodId, int foodNum, double weight, Date createTime) {
        this.id = id;
        this.roleId = roleId;
        this.foodId = foodId;
        this.foodNum = foodNum;
        this.weight = weight;
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

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
