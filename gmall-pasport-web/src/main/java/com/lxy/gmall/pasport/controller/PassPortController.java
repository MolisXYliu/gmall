package com.lxy.gmall.pasport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lxy.gmall.bean.UserInfo;
import com.lxy.gmall.pasport.config.JwtUtil;
import com.lxy.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-18 15:36
 */

@Controller
public class PassPortController {

    @Value("${token.key}")
    private String key;

    //调用服务层
    @Reference
    private UserService userService;


    @RequestMapping("index")
    public String index(HttpServletRequest request){
        //获取originUrl
        String originUrl = request.getParameter("originUrl");
        System.out.println("originUrl= "+originUrl);
        //保存originUrl
        request.setAttribute("originUrl", originUrl);
        return "index";
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(UserInfo userInfo,HttpServletRequest request){
        //salt 服务器的ip地址
        String salt = request.getHeader("X-forwarded-for");
        //调用登陆方法
        UserInfo info=userService.login(userInfo);
        if(info!=null){
            //如果登陆成功之后返回token
            //如何值作token
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", info.getId());
            map.put("nickName", info.getNickName());
            //生成token
            String token = JwtUtil.encode(key, map, salt);
            return token;
        }else{
            return "fail";
        }

    }


    @RequestMapping("verify")
    @ResponseBody
    public String verify(HttpServletRequest request){
        //用户登陆的认证
        //1.获取服务器的ip，token
        //2.key+ip 解密token 得到用户的信息 userId nickName
        //3.判断用户是否登录：key=user:userId:info value=uerInfo
        //4.userInfo!=null true success:false fail;
//      String salt = request.getHeader("X-forwarded-for");
        String token = request.getParameter("token");
        String salt = request.getParameter("salt");

        //调用jwt工具类
        Map<String, Object> map = JwtUtil.decode(token, key, salt);
        if(map!=null && map.size()>0){
            //获取userID
            String userId = (String) map.get("userId");
            //调用服务层查询用户是否已经登陆
            UserInfo userInfo=userService.verify(userId);
            if(userInfo!=null){
                return "success";
            }else{
                return "fail";
            }
        }
        return "fail";
    }


}
