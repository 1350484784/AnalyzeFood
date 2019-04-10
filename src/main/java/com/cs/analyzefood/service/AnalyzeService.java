package com.cs.analyzefood.service;

        import com.cs.analyzefood.entity.MealMade;
        import com.cs.analyzefood.entity.User;

        import java.util.List;

public interface AnalyzeService {

    double countRecommendEnergy(User user);

    double countPracticalEnergy(double dayCHO, double dayProtein, double dayFat);

    double countDayEnergy(List<MealMade> mealMades, int mealType);

}
