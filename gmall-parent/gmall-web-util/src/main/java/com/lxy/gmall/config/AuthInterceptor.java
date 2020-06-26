package com.lxy.gmall.config;


import com.alibaba.fastjson.JSON;
import com.lxy.gmall.util.HttpClientUtil;
import io.jsonwebtoken.impl.Base64UrlCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-18 22:14
 */

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    //多个拦截器执行的顺序？
    //跟配置文件中配置拦截器的顺序有关 //先进后出


    //用户进入控制器之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //如何获取到token
        String token=request.getParameter("newToken");
        //将token放入cookie中
//        Cookie cookie = new Cookie("token",token);
//        response.addCookie(cookie);
        //当token不为null时放入cookie中
        if(token!=null){
            CookieUtil.setCookie(request, response, "token", token, WebConst.COOKIE_MAXAGE, false);
        }
        //当用户访问非登陆之后的页面，登录之后继续访问其他业务模块时 url并没有newToken 但是后台可能将token放入到cookie中
        if(token==null){
           token=CookieUtil.getCookieValue(request, "token", false);
        }

         //从cookie中获取token 解密token
        if(token!=null){
            //开始解密token 获取nickName
            Map map=getUserMapByToken(token);
            String nickName = (String) map.get("nickName");
            //保存到作用域
            request.setAttribute("nickName", nickName);

        }

        //在拦截器中获取方法上的注解
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        //获取方法上的注解LoginRequire
        LoginRequire methodAnnotation = handlerMethod.getMethodAnnotation(LoginRequire.class);
        if(methodAnnotation!=null){
            //此时有注解 判断用户是否登陆了 调用verify
            //获取服务器上的ip地址
            String salt = request.getHeader("X-forwarded-for");
            //调用verify()认证
            String result = HttpClientUtil.doGet(WebConst.VERIFY_ADDRESS + "?token=" + token + "&salt=" + salt);
            if("success".equals(result)){
                //登陆 认证成功
                //保存一下userId
                //开始解密token 获取userId
                Map map=getUserMapByToken(token);
                String userId = (String) map.get("userId");
                //保存到作用域
                request.setAttribute("userId", userId);
                return true;
            }else{
                //认证失败！并且  methodAnnotation.autoRedirect()=true; 必须登陆
                if(methodAnnotation.autoRedirect()){
                    //必须登录 跳转到页面
                    //先获取到url
                    String requestURL = request.getRequestURL().toString();
                    System.out.println("requestURL= "+requestURL);
                    //将url进行转码
                    String encodeURL = URLEncoder.encode(requestURL, "UTF-8");
                    System.out.println("encodeURL= "+encodeURL );
                    response.sendRedirect(WebConst.LOGIN_ADDRESS+"?originUrl="+encodeURL);
                    return false;
                }
            }
        }
        return true;
    }

    //解密Token获取Map数据
    private Map getUserMapByToken(String token) {
        //获取中间部分
        String tokenUserInfo  = StringUtils.substringBetween(token, ".");
        //将tokenUserInfo 进行解密
        Base64UrlCodec base64UrlCodec = new Base64UrlCodec();
        //解码之后得到byte数组
        byte[] decode = base64UrlCodec.decode(tokenUserInfo);
        //先将decode转成字符串
        String mapJson  =null;
        try {
            mapJson  = new String(decode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //将字符串转换为map直接返回
        return JSON.parseObject(mapJson, Map.class);

    }

    //进入控制器之后，视图渲染之前
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    //视图渲染之后
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
