package com.cs.analyzefood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.cs.analyzefood.mapper")
@EnableScheduling
public class AnalyzefoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyzefoodApplication.class, args);
    }

}

