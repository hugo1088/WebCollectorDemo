package com.zhao.crawler.util;

import java.math.BigDecimal;

/** 百度地图坐标和火星坐标转换
 * Created by 明明如月 on 2017-03-22.
 */
public class CoodinateCovertor {
 
 
    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
 
    /**
     * 对double类型数据保留小数点后多少位
     *  高德地图转码返回的就是 小数点后6位，为了统一封装一下
     * @param digit 位数
     * @param in 输入
     * @return 保留小数位后的数
     */
     static double dataDigit(int digit,double in){
        return new BigDecimal(in).setScale(6,   BigDecimal.ROUND_HALF_UP).doubleValue();
 
    }
 
    /**
     * 将火星坐标转变成百度坐标
     * @param lngLat_gd 火星坐标（高德、腾讯地图坐标等）
     * @return 百度坐标
     */
    
 public static LngLat bd_encrypt(LngLat lngLat_gd)
    {
        double x = lngLat_gd.getLongitude(), y = lngLat_gd.getLantitude();
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x *  x_pi);
        return new LngLat(dataDigit(6,z * Math.cos(theta) + 0.0065),dataDigit(6,z * Math.sin(theta) + 0.006));
 
    }
    /**
     * 将百度坐标转变成火星坐标
     * @param lngLat_bd 百度坐标（百度地图坐标）
     * @return 火星坐标(高德、腾讯地图等)
     */
    static LngLat bd_decrypt(LngLat lngLat_bd)
    {
        double x = lngLat_bd.getLongitude() - 0.0065, y = lngLat_bd.getLantitude() - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        return new LngLat( dataDigit(6,z * Math.cos(theta)),dataDigit(6,z * Math.sin(theta)));
 
    }
 
//测试代码
    public static void main(String[] args) {
        //String s1 = bd_decrypt(114.065995, 22.609805); //百度，转之后
        //供应商：114.05941249915332,22.60414414304236
        //LngLat{longitude=114.059412, lantitude=22.604144}
        LngLat lngLat_bd = new LngLat(114.0664098889,22.608807267069);
        System.out.println(bd_decrypt(lngLat_bd));


    }
}
