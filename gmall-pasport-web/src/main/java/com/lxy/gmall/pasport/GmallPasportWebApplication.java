package com.lxy.gmall.pasport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.lxy.gmall")
public class GmallPasportWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPasportWebApplication.class, args);
    }

}
