package com.cs.analyzefood.additional;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.entity.vo.download.DownLoadFoodVo;
import com.cs.analyzefood.service.UserService;
import com.cs.analyzefood.util.JsonUtil;
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
public class DownloadFoods {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String BASE_URL = "http://www.boohee.com/";

    @Autowired
    protected UserService userService;

    public void down() {
        try {
            for (int i = 1; i <= 131; i++) {
                downloadCategory(""+i);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }


    private void downloadCategory(String categoryCode) {
        String categoryUrl = BASE_URL + "food/view_group/" + categoryCode;

        List<String> urls = new ArrayList<String>();

        //循环读10页,读取所有内容
        for (int pageNum = 1; pageNum <= 10; pageNum++) {
            String pageUrl = categoryUrl + "?page=" + pageNum;

            Document document = null;
            try {
                document = Jsoup.connect(pageUrl).timeout(3000).userAgent("Mozilla").get();
                if(document != null){
                    Elements elements = document.getElementsByClass("food-list").first().getElementsByTag("h4");
                    for(Element element:elements){
                        Element elementA = element.getElementsByTag("a").first();
                        urls.add(elementA.attributes().get("href"));
                    }
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        //遍历所有地址获取并下载
        for(String url:urls){
            System.out.println(url);
            Integer category = Integer.parseInt(categoryCode);
            downloadFoodInfo(url,category);
        }
    }

    private void downloadFoodInfo(String foodUrl,Integer category){
        String url = BASE_URL + foodUrl;
        DownLoadFoodVo downLoadFoodVo = new DownLoadFoodVo();
        int foodType = 85;
        if(category >= 1 && category <= 8){
            foodType = 11;
        }
        if(category >= 9 && category <= 16){
            foodType = 21;
        }
        if(category >= 17 && category <= 24){
            foodType = 31;
        }
        if(category >= 25 && category <= 32){
            foodType = 41;
        }
        if(category >= 33 && category <= 43){
            foodType = 51;
        }
        if(category >= 43 && category <= 49){
            foodType = 53;
        }
        if(category >= 50 && category <= 53){
            foodType = 54;
        }
        if(category >= 54 && category <= 58){
            foodType = 61;
        }
        if(category >= 82 && category <= 84){
            foodType = 81;
        }
        if(category >= 59 && category <= 81){
            foodType = 82;
        }
        if(category >= 85 && category <= 131){
            foodType = 83;
        }
        //
        downLoadFoodVo.setFootType(foodType);
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(3000).userAgent("Mozilla").get();
            if (document != null) {
                Element elementContainer = document.getElementsByClass("widget-group-content").first();
                //
                downLoadFoodVo.setName(elementContainer.getElementsByClass("crumb").first().ownText().replaceAll("/","").trim());
                elementContainer = elementContainer.getElementsByClass("widget-food-detail").first();
                Elements elementsContent = elementContainer.getElementsByClass("content");
                Element elementIntro = elementsContent.get(0),
                        elementNutrition = elementsContent.get(1),
                        elementMeasurement = elementsContent.get(2);
                //营养信息
                Elements dd = elementNutrition.getElementsByTag("dd");
                for(Element element:dd){
                    String nutritionName = element.getElementsByClass("dt").first().text();
                    String nutritionNum = element.getElementsByClass("dd").first().text();
                    if(!"营养素".equals(nutritionName) && !"一".equals(nutritionNum)){
                        switch (nutritionName){
                            case "热量(大卡)":
                                downLoadFoodVo.setEnergy(new Double(nutritionNum));break;
                            case "碳水化合物(克)":
                                downLoadFoodVo.setCarbohydrate(new Double(nutritionNum));break;
                            case "脂肪(克)":
                                downLoadFoodVo.setFat(new Double(nutritionNum));break;
                            case "蛋白质(克)":
                                downLoadFoodVo.setProtein(new Double(nutritionNum));break;
                            case "纤维素(克)":
                                downLoadFoodVo.setFiber(new Double(nutritionNum));break;
                            case "维生素A(微克)":
                                downLoadFoodVo.setVa(new Double(nutritionNum));break;
                            case "维生素C(毫克)":
                                downLoadFoodVo.setVc(new Double(nutritionNum));break;
                            case "维生素E(毫克)":
                                downLoadFoodVo.setVe(new Double(nutritionNum));break;
                            case "硫胺素(毫克)":
                                downLoadFoodVo.setVb1(new Double(nutritionNum));break;
                            case "核黄素(毫克)":
                                downLoadFoodVo.setVb2(new Double(nutritionNum));break;
                            case "烟酸(毫克)":
                                downLoadFoodVo.setNiacin(new Double(nutritionNum));break;
                            case "胆固醇(毫克)":
                                downLoadFoodVo.setCholesterol(new Double(nutritionNum));break;
                            case "钙(毫克)":
                                downLoadFoodVo.setCa(new Double(nutritionNum));break;
                            case "铁(毫克)":
                                downLoadFoodVo.setFe(new Double(nutritionNum));break;
                            case "钠(毫克)":
                                downLoadFoodVo.setNa(new Double(nutritionNum));break;
                            default :
                                logger.error("无法解析元素: "+nutritionName+":"+nutritionNum);

                        }
                    }

                }
                Food oldFood = userService.findFoodByName(downLoadFoodVo.getName());
                if(oldFood == null){
                    userService.insertDownloadFood(downLoadFoodVo);
                }
            }


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
