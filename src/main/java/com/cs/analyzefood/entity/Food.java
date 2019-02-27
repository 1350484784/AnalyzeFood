package com.cs.analyzefood.entity;

public class Food {
    private int foodId;
    private String foodName;
    private int typeId;
    private int eat_part;       //可食部分
    private double energy;      //能量
    private double moisture;    //水分
    private double protein;     //蛋白质
    private double fat;         //脂肪
    private double fiber;       //纤维
    private double carbohydrate;//碳水化合物
    private double va;
    private double vb1;
    private double vb2;
    private double niacin;      //烟酸
    private double ve;
    private double na;
    private double ca;
    private double fe;
    private double vc;
    private double cholesterol; //胆固醇

    public Food() {
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getEat_part() {
        return eat_part;
    }

    public void setEat_part(int eat_part) {
        this.eat_part = eat_part;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getVa() {
        return va;
    }

    public void setVa(double va) {
        this.va = va;
    }

    public double getVb1() {
        return vb1;
    }

    public void setVb1(double vb1) {
        this.vb1 = vb1;
    }

    public double getVb2() {
        return vb2;
    }

    public void setVb2(double vb2) {
        this.vb2 = vb2;
    }

    public double getNiacin() {
        return niacin;
    }

    public void setNiacin(double niacin) {
        this.niacin = niacin;
    }

    public double getVe() {
        return ve;
    }

    public void setVe(double ve) {
        this.ve = ve;
    }

    public double getNa() {
        return na;
    }

    public void setNa(double na) {
        this.na = na;
    }

    public double getCa() {
        return ca;
    }

    public void setCa(double ca) {
        this.ca = ca;
    }

    public double getFe() {
        return fe;
    }

    public void setFe(double fe) {
        this.fe = fe;
    }

    public double getVc() {
        return vc;
    }

    public void setVc(double vc) {
        this.vc = vc;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }
}
