package com.lxy.gmall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.lxy.gmall.order.mapper")
@ComponentScan(basePackages = "com.lxy.gmall")
@EnableTransactionManagement//两张表以上要开启事务
public class GmallOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallOrderServiceApplication.class, args);
    }

}
