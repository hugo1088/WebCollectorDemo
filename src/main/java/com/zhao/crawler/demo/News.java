package com.zhao.crawler.demo;

/**
 * Created by PC on 2018/7/15.
 */
public class News {
    private int id;
    private String title;
    private String content;

    public News(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) {
        String s1 = bd_decrypt(114.065995, 22.609805);
        System.out.println("供应商："+s1);



    }

    /**
     * 百度地图坐标转高德地图
     * @param bd_lon 经度（值较大）
     * @param bd_lat 纬度（值较小）
     */

    public static String bd_decrypt(double bd_lon, double bd_lat)
    {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

        double x = bd_lon - 0.0065, y = bd_lat - 0.006;

        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);

        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);

        return z * Math.cos(theta)+","+z * Math.sin(theta);
    }


}
