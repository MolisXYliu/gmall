package com.lxy.gmall.user.controller;

import com.lxy.gmall.bean.UserInfo;
import com.lxy.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-09 20:11
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("findAll")
    public List<UserInfo>findAll(){
        return userService.findAll();
    }

}
