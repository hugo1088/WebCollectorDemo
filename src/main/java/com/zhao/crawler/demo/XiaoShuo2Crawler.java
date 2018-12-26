package com.zhao.crawler.demo;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.zhao.crawler.util.RedisUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Crawling news from hfut news
 *
 * @author hu
 */
public class XiaoShuo2Crawler extends BreadthCrawler {
    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     *                  information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     *                  links which match regex rules from pag
     */
    public XiaoShuo2Crawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        //天道图书馆
        this.addSeed("https://www.biqugeg.com/17_17139/");
        this.addRegex("https://www.biqugeg.com/17_17139/.*");
        setThreads(50);
        getConf().setTopN(100);
//        setResumable(true);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        /*if page is news page*/
        // if (Pattern.matches("http://www.biqiuge.com/book/4772/.+html", url)) {
        if (Pattern.matches("https://www.biqugeg.com/17_17139/.+html", url)) {
            /*extract title and content of news by css selector*/
            String title = page.select("div[class=content]>h1").first().text();
            Elements elements = page.select("div#content");
            String content = elements.html().replaceAll(";&nbsp;","").replaceAll("&nbsp","").replaceAll("请记住本书首发域名：www.biqiuge.com。笔趣阁手机版阅读网址：m.biqiuge.com","");
            int id = chineseNumber2Int(title);

            Element nextPage = page.select("div[class=page_chapter]>ul>li").get(2).select("a").first();
            String linkHref = "https://www.biqugeg.com"+nextPage.attr("href"); //取得链接地址/book/4772/6922557.html
            System.out.println("下一章："+linkHref);

            //String content = page.("div#content");
            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);
            System.out.println("id:\n" + id);
            News news = new News(id,title,content);
            Map<String,String> newsMap = new HashMap<>();
            newsMap.put("id",String.valueOf(id));
            newsMap.put("title",title);
            newsMap.put("content",content);
            try {
                Jedis jedis = RedisUtil.getJedis();
                String hmset = jedis.hmset("news:" + id, newsMap);
                System.out.println("保存新闻："+hmset);
                RedisUtil.returnResource(jedis);
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            next.add(linkHref);
            /*If you want to add urls to crawl,add them to nextLink   */
            /*WebCollector automatically filters links that have been fetched before*/
            /*If autoParse is true and the link you add to nextLinks does not match the
              regex rules,the link will also been filtered.*/
            //next.add("http://xxxxxx.com");
        }
    }

    public static void main(String[] args) throws Exception {
        XiaoShuo2Crawler crawler = new XiaoShuo2Crawler("crawl", true);
        crawler.start(4);
    }

    /**
     * 中文數字转阿拉伯数组【十万九千零六十  --> 109060】
     * @author 雪见烟寒
     * @param chineseNumber
     * @return
     */
    @SuppressWarnings("unused")
    private static int chineseNumber2Int(String chineseNumber){
        int beginIndex = chineseNumber.indexOf("第")+1;
        int endIndex = chineseNumber.indexOf("章");
        chineseNumber = chineseNumber.substring(beginIndex,endIndex);
        System.out.println(chineseNumber);
        try {
            return Integer.parseInt(chineseNumber);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        int result = 0;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        char[] chArr = new char[]{'十','百','千','万','亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if(b){//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                            case 0:
                                temp *= 10;
                                break;
                            case 1:
                                temp *= 100;
                                break;
                            case 2:
                                temp *= 1000;
                                break;
                            case 3:
                                temp *= 10000;
                                break;
                            case 4:
                                temp *= 100000000;
                                break;
                            default:
                                break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
    }

}