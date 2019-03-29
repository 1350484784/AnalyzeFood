package com.cs.analyzefood.additional;

import com.cs.analyzefood.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DownladFoods {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String BASE_URL = "http://www.boohee.com/";

    @Autowired
    protected UserService userService;

    public void down() {
        try {
            for (int i = 1; i <= 10; i++) {
                downloadCategory(""+i);
            }
            downloadCategory("view_menu");
        }catch(Exception e){
            logger.debug(e.getMessage());
        }
    }


    private void downloadCategory(String categoryCode) throws IOException {
        String categoryUrl = BASE_URL + "food/group/" + categoryCode;

        List<String> urls = new ArrayList<String>();

        //循环读10页,读取所有内容
        for (int pageNum = 1; pageNum <= 10; pageNum++) {
            String pageUrl = categoryUrl + "?page=" + pageNum;

            Document document = Jsoup.connect(pageUrl).timeout(3000).userAgent("Mozilla").get();
            Elements elements = document.getElementsByClass("food-list").first().getElementsByTag("h4");
            for(Element element:elements){
                Element elementA = element.getElementsByTag("a").first();
                urls.add(elementA.attributes().get("href"));
            }
        }
        //遍历所有地址获取并下载
//        for(String url:urls){
//            Integer category = categoryCode.equals("view_menu")?11:Integer.parseInt(categoryCode);
//            downloadFoodInfo(sqlSession,url,category);
//        }
    }

}
