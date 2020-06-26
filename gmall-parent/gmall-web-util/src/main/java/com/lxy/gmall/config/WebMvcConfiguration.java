package com.lxy.gmall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-18 23:34
 */

@Configuration //xxx.xml
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;

    //拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截器
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
        //添加拦截器
        super.addInterceptors(registry);
    }
}
