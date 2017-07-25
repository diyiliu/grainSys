package com.diyiliu.util;

import java.util.Date;
import java.util.UUID;

/**
 * Description: CommonUtil
 * Author: DIYILIU
 * Update: 2017-07-24 16:31
 */
public class CommonUtil {

    public static String createID() {
        return createID(new Date());
    }

    public static String createID(Date date) {
        String uu = UUID.randomUUID().toString().substring(24).toUpperCase();
        String ti = String.format("%1$ty%1$tm%1$td%1$tH", date);

        return uu + ti;
    }
}
