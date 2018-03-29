package com.example.iman.weatherapp;

/**
 * Created by Iman on 3/29/2018.
 */

public class PublicMethods {

    public static String getC(String f){

        double ff=Double.parseDouble(f);
        double c=(ff-32)/1.8;
        int x=(int) c;
        return x+"";
    }
}
