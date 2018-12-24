package com.zhao.crawler.util;
 
public class LocationUtils {
	private static double EARTH_RADIUS = 6378.137;
 
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
 
	/**
	 * 通过经纬度获取距离(单位：米)
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return 距离
	 */
	public static double getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		return s;
	}
 
	public static void main(String[] args) {
		LngLat lngLat_bd = new LngLat(114.065948,22.609866);//百度的经纬度,
		LngLat lngLat = CoodinateCovertor.bd_decrypt(lngLat_bd);//转百度
		System.out.println(lngLat.toString());
		double distance = getDistance(lngLat.getLantitude(), lngLat.getLongitude(), 22.604292, 114.058997);
		System.out.println("距离1:" + distance  + "米");

		double distance2 = getDistance(22.603861, 114.058863, 22.604292, 114.058997);
		System.out.println("距离2:" + distance2  + "米");
	}
}
