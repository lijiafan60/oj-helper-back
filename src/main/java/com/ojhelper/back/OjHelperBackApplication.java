package com.ojhelper.back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ojhelper.back.mapper")
public class OjHelperBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjHelperBackApplication.class, args);
    }

}
