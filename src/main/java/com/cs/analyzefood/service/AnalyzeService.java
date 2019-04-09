package com.cs.analyzefood.service;

        import com.cs.analyzefood.entity.User;

public interface AnalyzeService {

    double countRecommendEnergy(User user);

    double countPracticalEnergy(double dayCHO, double dayProtein, double dayFat);
}
