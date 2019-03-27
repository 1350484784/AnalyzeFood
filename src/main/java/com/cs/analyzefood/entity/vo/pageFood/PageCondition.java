package com.cs.analyzefood.entity.vo.pageFood;

public class PageCondition {
    private int currentPage;
    private int[] foodType;
    private String foodName;
    private Double min;
    private Double max;

    public PageCondition() {
    }

    public PageCondition(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int[] getFoodType() {
        return foodType;
    }

    public void setFoodType(int[] foodType) {
        this.foodType = foodType;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}
