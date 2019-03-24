package com.cs.analyzefood.entity;

public class MealMade {
    private int id;
    private int mealId;
    private int foodId;
    private double num;
    private int mealType;

    private Food food;

    public MealMade() {
    }

    public MealMade(int mealId, int foodId, double num, int mealType) {
        this.mealId = mealId;
        this.foodId = foodId;
        this.num = num;
        this.mealType = mealType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public int getMealType() {
        return mealType;
    }

    public void setMealType(int mealType) {
        this.mealType = mealType;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
