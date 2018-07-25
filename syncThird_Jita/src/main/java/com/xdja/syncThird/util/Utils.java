package com.xdja.syncThird.util;

import java.util.UUID;

/**
 * Created by yxb on  2018/7/4
 */
public class Utils {

    public synchronized static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static int checkLevel(String strLevel, String strDepartment) {
        if (null != strLevel && !strLevel.equals("") && !strLevel.equalsIgnoreCase("null")) {
            return Integer.parseInt(strLevel);
        } else {
            strDepartment = (strDepartment + "0000000000").substring(0, 12);
            if (strDepartment.substring(2, 12).equals("0000000000")) {
                return 1;
            }
            if (strDepartment.substring(4, 12).equals("00000000")) {
                return 2;
            }
            if (strDepartment.substring(6, 12).equals("000000")) {
                return 3;
            }
            if (strDepartment.substring(8, 12).equals("0000")) {
                return 5;
            }
            if (strDepartment.substring(10, 12).equals("00")) {
                return 6;
            }
            return 6;
        }
    }

    public static void main(String[] args) {
    }
}
