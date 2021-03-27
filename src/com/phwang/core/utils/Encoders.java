/*
 ******************************************************************************
 *
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

public class Encoders {
    public static final int DATA_LENGTH_SIZE = 2;

    public static String iEncodeRaw(int number_val, int size_val) {
        String str = Integer.toString(number_val);

        StringBuilder buf = new StringBuilder();
        for (int i = str.length(); i < size_val; i++) {
            buf.append('0');
        }
        buf.append(str);
        return buf.toString();
    }
    public static String iEncodeRaw1(int number_val) { return iEncodeRaw(number_val, 1); }
    public static String iEncodeRaw2(int number_val) { return iEncodeRaw(number_val, 2); }
    public static String iEncodeRaw3(int number_val) { return iEncodeRaw(number_val, 3); }
    public static String iEncodeRaw4(int number_val) { return iEncodeRaw(number_val, 4); }
    public static String iEncodeRaw5(int number_val) { return iEncodeRaw(number_val, 5); }
    public static String iEncodeRaw6(int number_val) { return iEncodeRaw(number_val, 6); }

    public static String iEncode111(int number_val, int size_val) {
        return iEncodeRaw(number_val, size_val);
    }

    public static String iEncodeLen(int number_val, int size_val) {
        String str = iEncodeRaw(number_val, size_val);
        return sEncode2(str);
    }
    public static String iEncodeLen1(int number_val) { return iEncodeLen(number_val, 1); }
    public static String iEncodeLen2(int number_val) { return iEncodeLen(number_val, 2); }
    public static String iEncodeLen3(int number_val) { return iEncodeLen(number_val, 3); }
    public static String iEncodeLen4(int number_val) { return iEncodeLen(number_val, 4); }
    public static String iEncodeLen5(int number_val) { return iEncodeLen(number_val, 5); }
    public static String iEncodeLen6(int number_val) { return iEncodeLen(number_val, 6); }

    public static int iDecodeRaw(String str_val) {
        int str_len = str_val.length();
        int val = 0;

        for (int i = 0; i < str_len; i++) {
            val += str_val.charAt(i) - '0';
            if (i < str_len - 1) {
                val *= 10;
            }
        }
        return val;
    }

    public static int iDecode111(String str_val) {
        return iDecodeRaw(str_val);
    }

    public static int iDecodeLen(String str_val) {
        String str = sDecode2(str_val);
        return iDecodeRaw(str);
    }

    public static String sEncode(String str_val, int size_val) {
        StringBuilder buf = new StringBuilder();
        buf.append(Encoders.iEncodeRaw(str_val.length(), size_val));
        buf.append(str_val);
        return buf.toString();
    }
    public static String sEncode2(String str_val) { return sEncode(str_val, 2); }
    public static String sEncode5(String str_val) { return sEncode(str_val, 5); }

    public static String sDecode(String str_val, int size_val) {
        int len = Encoders.iDecodeRaw(str_val.substring(0, size_val));
        return str_val.substring(size_val, size_val + len);
    }
    public static String sDecode1(String str_val) { return sDecode(str_val, 1); }
    public static String sDecode2(String str_val) { return sDecode(str_val, 2); }
    public static String sDecode5(String str_val) { return sDecode(str_val, 5); }

    public static String sDecode_(String str_val, int size_val) {
        return str_val.substring(size_val + Encoders.iDecodeRaw(str_val.substring(0, size_val)));
    }
    public static String sDecode1_(String str_val) { return sDecode_(str_val, 1); }
    public static String sDecode2_(String str_val) { return sDecode_(str_val, 2); }
    public static String sDecode5_(String str_val) { return sDecode_(str_val, 5); }

    public static String sSubstring(String str_val, int size_val) {
        int len = Encoders.iDecodeRaw(str_val.substring(0, size_val));
        return str_val.substring(0, size_val + len);
    }
    public static String sSubstring1(String str_val) { return sSubstring(str_val, 1); }
    public static String sSubstring2(String str_val) { return sSubstring(str_val, 2); }
    public static String sSubstring5(String str_val) { return sSubstring(str_val, 5); }


    public static String sSubstring_(String str_val, int size_val) {
        return str_val.substring(size_val + Encoders.iDecodeRaw(str_val.substring(0, size_val)));
    }
    public static String sSubstring1_(String str_val) { return sSubstring_(str_val, 1); }
    public static String sSubstring2_(String str_val) { return sSubstring_(str_val, 2); }
    public static String sSubstring5_(String str_val) { return sSubstring_(str_val, 5); }
}
