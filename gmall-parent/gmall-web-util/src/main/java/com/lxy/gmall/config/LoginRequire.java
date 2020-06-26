package com.lxy.gmall.config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-19 0:13
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)//被编译成.class或加载到虚拟机的时候仍然有效
public @interface LoginRequire {

    //true:则表示需要登录 否则不需要登陆！
    boolean autoRedirect() default true;
}
