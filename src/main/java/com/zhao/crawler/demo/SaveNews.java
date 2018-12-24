package com.zhao.crawler.demo;

import com.zhao.crawler.util.ForFile;
import com.zhao.crawler.util.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by PC on 2018/7/15.
 */
public class SaveNews {
    public static void main(String[] args)throws  Exception {
        ForFile.createFile("遮天");
        Jedis jedis = RedisUtil.getJedis();
        Set<String> keys = jedis.keys("*");
        List<Integer> keyList = new ArrayList<>();
        for (String key : keys) {
            String[] split = key.split(":");
            keyList.add(Integer.parseInt(split[1]));
        }
        Collections.sort(keyList);
        for (Integer id : keyList) {
            System.out.println(id);
            Map<String, String> map = jedis.hgetAll("news:"+id);
            String title = map.get("title");
            System.out.println(title);
            //ForFile.writeFileContent("D:\\file\\遮天.txt",title);
            String content = map.get("content").replaceAll("<br>","").replaceAll("www.biqugeg.com","").replaceAll("html","").replaceAll("手机用户请浏览阅读，更优质的阅读体验。","").replaceAll("天才一秒记住本站地址：","").replaceAll("笔趣阁手机版阅读网址：","").replaceAll("m.biqugeg.com","");
            ForFile.writeFileContent("D:\\file\\遮天.txt",title+"\n"+content);

        }
        RedisUtil.returnResource(jedis);
    }
}
