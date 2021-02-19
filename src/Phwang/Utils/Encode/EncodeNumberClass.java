/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.Encode;

public class EncodeNumberClass {
    public static String EncodeNumber(int number_val, int size_val)
    {
        //String str = number_val.ToString();

        var buf = "";
        /*
        for (var i = str.Length; i < size_val; i++)
        {
            buf = buf + "0";
        }
        buf = buf + str;
        */
        return buf;
    }

    /*
    public static int DecodeNumber(String str_val)
    {
        int str_len = str_val.Length;
        int val = 0;

        for (int i = 0; i < str_len; i++)
        {
            val += str_val[i] - '0';
            if (i < str_len - 1)
            {
                val *= 10;
            }
        }

        return val;
    }
    */
}
