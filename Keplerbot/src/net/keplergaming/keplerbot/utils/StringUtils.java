package net.keplergaming.keplerbot.utils;

import java.util.ArrayList;

public class StringUtils {

    public static String joinString(ArrayList<String> array)
    {
        StringBuilder var1 = new StringBuilder();

        for (int var2 = 0; var2 < array.size(); ++var2)
        {
            String var3 = array.get(var2).toString();

            if (var2 > 0)
            {
                if (var2 == array.size() - 1)
                {
                    var1.append(" and ");
                }
                else
                {
                    var1.append(", ");
                }
            }

            var1.append(var3);
        }

        return var1.toString();
    }
    
    public static String[] dropFirstString(String[] array) {
        String[] newArray = new String[array.length - 1];

        for (int i = 1; i < array.length; ++i)
        {
        	newArray[i - 1] = array[i];
        }

        return newArray;
    }
    
    public static String[] dropStrings(String[] array, int amount) {
        String[] newArray = new String[array.length - amount];

        for (int i = amount; i < array.length; ++i)
        {
        	newArray[i - amount] = array[i];
        }

        return newArray;
    }
    
    public static String joinString(String[] array)
    {
        StringBuilder var1 = new StringBuilder();

        for (int var2 = 0; var2 < array.length; ++var2)
        {
            String var3 = array[var2];

            if (var2 > 0)
            {
            	var1.append(" ");
            }

            var1.append(var3);
        }

        return var1.toString();
    }
    
}
