package com.cs.analyzefood;

import com.cs.analyzefood.additional.DownladFoods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalyzefoodApplicationTests {

    @Autowired
    private DownladFoods downladFoods;

    @Test
    public void test() {

    }

}

