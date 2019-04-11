package com.cs.analyzefood.entity.vo.analyze;

public class ResultEachFoodVo {
    private double gu;
    private double dou;
    private double shu;
    private double guo;
    private double rou;
    private double nai;
    private double dan;
    private double yu;
    private double you;
    private double other;

    public ResultEachFoodVo() {
    }

    public ResultEachFoodVo(double gu, double dou, double shu, double guo, double rou, double nai, double dan, double yu, double you, double other) {
        this.gu = gu;
        this.dou = dou;
        this.shu = shu;
        this.guo = guo;
        this.rou = rou;
        this.nai = nai;
        this.dan = dan;
        this.yu = yu;
        this.you = you;
        this.other = other;
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

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }
}
