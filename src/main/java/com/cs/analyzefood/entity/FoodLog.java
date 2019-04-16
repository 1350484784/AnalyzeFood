package com.cs.analyzefood.entity;

import java.util.Date;

public class FoodLog {
    private int id;
    private int roleId;
    private int foodId;
    private int type;
    private int foodNum;
    private double weight;
    private Date createTime;

    public FoodLog() {
    }

    public FoodLog(int roleId, int foodId, int type, int foodNum, Date createTime) {
        this.roleId = roleId;
        this.foodId = foodId;
        this.type = type;
        this.foodNum = foodNum;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
