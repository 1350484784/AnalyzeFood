package com.cs.analyzefood.entity.vo.analyze;

public class ResultVo {
    private double recommendEnergy;
    private double practicalEnergy;

    public ResultVo() {
    }

    public ResultVo(double recommendEnergy, double practicalEnergy) {
        this.recommendEnergy = recommendEnergy;
        this.practicalEnergy = practicalEnergy;
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
}
