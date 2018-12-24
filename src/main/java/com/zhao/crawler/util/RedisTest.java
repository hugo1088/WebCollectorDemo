package com.zhao.crawler.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//参考地址：http://840327220.iteye.com/blog/2274812
/**
 * 描述：redis测试实例
 * @author Paul
 * 日期：2016-10-21
 */
public class RedisTest {
 
	 /**####################################################实际操作封装#######################################################*/
    /**
     * 添加String类型
     */
    public static void setString(){
     for(int i=0;i<100;i++){
		 RedisUtils.setString("key"+i, "value"+i);
    	 System.out.println(RedisUtils.getString("key")+i);
     }
    }
    
    /**
     * List
     */
    
    public static void setList(){
    	 List<User> userList=new ArrayList<User>();
    		userList.add(new User("11", "jibaole", 12));
    		userList.add(new User("22", "hdiwoq", 23));
    		userList.add(new User("33", "dwq",45));
    		RedisUtils.setList("userList", userList);
    		 List<User> list = RedisUtils.getList("userList");
    		 for(User user : list){  
    	         System.out.println("user:" + user.getUserName());  
    	     } 
    }
    
    /**
     * Map
     */
	public static void setMap() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<User> map1 = new ArrayList<User>();
		map1.add(new User("11", "jibaole", 12));
		map1.add(new User("22", "hdiwoq", 23));
		map1.add(new User("33", "dwq", 45));
		resultMap.put("map1", map1);
		List<User> map2 = new ArrayList<User>();
		map2.add(new User("111", "jibaole22", 120));
		map2.add(new User("222", "hdiwoq22", 230));
		map2.add(new User("333", "dwq22", 450));
		resultMap.put("map2", map2);
 
		RedisUtils.setMap("resultMap", resultMap);
		Map<String, Object> map = RedisUtils.getMap("resultMap");
		for (String key : map.keySet()) {
			@SuppressWarnings("unchecked")
			List<User> list = (List<User>) map.get(key);
			for (User user : list) {
				System.out.println("user:" + user.getUserName());
			}
		}
	}
    
   public static void main(String[] args) {
	   //setList();
	   setMap();
}
}
