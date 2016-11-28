package com.example.legye.wouldyourather.dataaccess;

/**
 * Created by legye on 2016. 11. 21..
 */

public class Numeric {

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
