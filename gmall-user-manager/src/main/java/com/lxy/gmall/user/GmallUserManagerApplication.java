package com.lxy.gmall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.lxy.gmall.user.mapper")
@ComponentScan(basePackages = "com.lxy.gmall")
public class GmallUserManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallUserManagerApplication.class, args);
    }

}
