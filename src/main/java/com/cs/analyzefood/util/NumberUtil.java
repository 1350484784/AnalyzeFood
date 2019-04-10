package com.cs.analyzefood.util;

import java.text.DecimalFormat;

public class NumberUtil {

    public static double formatDouble(double d){
        DecimalFormat df = new DecimalFormat("#.00");
        return new Double(df.format(d));
    }
}
