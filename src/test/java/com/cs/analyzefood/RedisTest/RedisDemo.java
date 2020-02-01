package com.cs.analyzefood.RedisTest;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.util.JsonUtil;
import com.cs.analyzefood.util.RedisUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemo {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.key.prefix.foodInfo}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Test
    public void contextLoads() {
        String str = "1234";
        redisUtil.set("test", str);
        System.out.println(ToStringBuilder
                .reflectionToString(redisUtil.get("test")));
    }

    @Test
    public void del(){
        redisUtil.del("list_foodList_3");
    }

    @Test
    public void redisValPut() {
        Food food1 = new Food();
        food1.setFoodId(1);
        food1.setFoodName("茄子");
        Food food2 = new Food();
        food2.setFoodId(2);
        food2.setFoodName("瓜");
        List<Food> foodList = new ArrayList<>();
        foodList.add(food1);
        foodList.add(food2);

        redisUtil.set("val_foodList", JsonUtil.toJson(foodList));

        System.out.println(JsonUtil.fromJson(String.valueOf(redisUtil.get("val_foodList")), List.class));
    }


    @Test
    public void redisListPut() {
        Food food1 = new Food();
        food1.setFoodId(1);
        food1.setFoodName("茄子");
        Food food2 = new Food();
        food2.setFoodId(2);
        food2.setFoodName("瓜");
        List<Food> foodList = new ArrayList<>();
        foodList.add(food1);
        foodList.add(food2);

        redisUtil.lSet("list_foodList", JsonUtil.toJson(foodList));

        System.out.println(JsonUtil.toJson(redisUtil.lGet("list_foodList", 0 , -1)));
    }

    @Test
    public void redisListPut2() {
        Food food1 = new Food();
        food1.setFoodId(1);
        food1.setFoodName("茄子");
        Food food2 = new Food();
        food2.setFoodId(2);
        food2.setFoodName("瓜");
        List<String> foodList = new ArrayList<>();
        foodList.add(JsonUtil.toJson(food1));
        foodList.add(JsonUtil.toJson(food2));

        redisTemplate.opsForList().leftPushAll("list_foodList_2", foodList);

        System.out.println(JsonUtil.toJson(redisUtil.lGet("list_foodList_2", 0 , -1)));

//        redisTemplate.opsForList().leftPushAll("test2", "1", "2", "3", "4");
//        System.out.println(redisTemplate.opsForList().range("test2", 0, -1));
    }

    @Test
    public void redisHashPut() {
        Food food1 = new Food();
        food1.setFoodId(3);
        food1.setFoodName("西红柿");
        Food food2 = new Food();
        food2.setFoodId(4);
        food2.setFoodName("苹果");
        List<String> foodList = new ArrayList<>();
        foodList.add(JsonUtil.toJson(food1));
        foodList.add(JsonUtil.toJson(food2));

        redisTemplate.boundHashOps("foodList").put("type:1", foodList);

        System.out.println(redisTemplate.boundHashOps("foodList").get("type:1"));
    }
}
