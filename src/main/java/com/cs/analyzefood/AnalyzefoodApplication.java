package com.cs.analyzefood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cs.analyzefood.mapper")
public class AnalyzefoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyzefoodApplication.class, args);
    }

}

