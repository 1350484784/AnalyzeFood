package com.cs.analyzefood.entity.vo.analyze;

public class ResultVo {
    private double recommendEnergy;
    private double practicalEnergy;

    private double dayZao;
    private double dayZhong;
    private double dayWan;

    private double dayCHOPer;
    private double dayProteinPer;
    private double dayFatPer;

    private ResultEachFoodVo eachFoodVo;

    public ResultVo() {
    }

    public ResultVo(double recommendEnergy, double practicalEnergy, double dayZao, double dayZhong, double dayWan, double dayCHOPer, double dayProteinPer, double dayFatPer) {
        this.recommendEnergy = recommendEnergy;
        this.practicalEnergy = practicalEnergy;
        this.dayZao = dayZao;
        this.dayZhong = dayZhong;
        this.dayWan = dayWan;
        this.dayCHOPer = dayCHOPer;
        this.dayProteinPer = dayProteinPer;
        this.dayFatPer = dayFatPer;
    }

    public double getRecommendEnergy() {
        return recommendEnergy;
    }

    public void setRecommendEnergy(double recommendEnergy) {
        this.recommendEnergy = recommendEnergy;
    }

    public double getPracticalEnergy() {
        return practicalEnergy;
    }

    public void setPracticalEnergy(double practicalEnergy) {
        this.practicalEnergy = practicalEnergy;
    }

    public double getDayZao() {
        return dayZao;
    }

    public void setDayZao(double dayZao) {
        this.dayZao = dayZao;
    }

    public double getDayZhong() {
        return dayZhong;
    }

    public void setDayZhong(double dayZhong) {
        this.dayZhong = dayZhong;
    }

    public double getDayWan() {
        return dayWan;
    }

    public void setDayWan(double dayWan) {
        this.dayWan = dayWan;
    }

    public double getDayCHOPer() {
        return dayCHOPer;
    }

    public void setDayCHOPer(double dayCHOPer) {
        this.dayCHOPer = dayCHOPer;
    }

    public double getDayProteinPer() {
        return dayProteinPer;
    }

    public void setDayProteinPer(double dayProteinPer) {
        this.dayProteinPer = dayProteinPer;
    }

    public double getDayFatPer() {
        return dayFatPer;
    }

    public void setDayFatPer(double dayFatPer) {
        this.dayFatPer = dayFatPer;
    }

    public ResultEachFoodVo getEachFoodVo() {
        return eachFoodVo;
    }

    public void setEachFoodVo(ResultEachFoodVo eachFoodVo) {
        this.eachFoodVo = eachFoodVo;
    }
}
