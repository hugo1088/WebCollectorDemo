package com.zhao.crawler.util;

/**
 * Created by PC on 2018/7/15.
 */
public class StringUtils {
    public static boolean isNotEmpty(String value) {
        if(value == null ||"".equals(value)) {
            return false;
        }
        return true;
    }
}
