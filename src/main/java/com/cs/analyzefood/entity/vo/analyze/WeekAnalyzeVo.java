package com.cs.analyzefood.entity.vo.analyze;

import java.util.Date;

public class WeekAnalyzeVo {
    private double[] weekEnergy;
    private double[] weekCHO;
    private double[] weekProtein;
    private double[] weekFat;
    private boolean isReach;
    private Date[] createTime;

    public WeekAnalyzeVo() {
    }

    public WeekAnalyzeVo(double[] weekEnergy, double[] weekCHO, double[] weekProtein, double[] weekFat, boolean isReach) {
        this.weekEnergy = weekEnergy;
        this.weekCHO = weekCHO;
        this.weekProtein = weekProtein;
        this.weekFat = weekFat;
        this.isReach = isReach;
    }

    public double[] getWeekEnergy() {
        return weekEnergy;
    }

    public void setWeekEnergy(double[] weekEnergy) {
        this.weekEnergy = weekEnergy;
    }

    public double[] getWeekCHO() {
        return weekCHO;
    }

    public void setWeekCHO(double[] weekCHO) {
        this.weekCHO = weekCHO;
    }

    public double[] getWeekProtein() {
        return weekProtein;
    }

    public void setWeekProtein(double[] weekProtein) {
        this.weekProtein = weekProtein;
    }

    public double[] getWeekFat() {
        return weekFat;
    }

    public void setWeekFat(double[] weekFat) {
        this.weekFat = weekFat;
    }

    public boolean isReach() {
        return isReach;
    }

    public void setReach(boolean reach) {
        isReach = reach;
    }

    public Date[] getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date[] createTime) {
        this.createTime = createTime;
    }
}
