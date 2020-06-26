package com.lxy.gmall.service;

import com.lxy.gmall.bean.UserAddress;
import com.lxy.gmall.bean.UserInfo;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-09 19:38
 */
public interface UserService {

    /**
     * 查询所有数据
     * @return
     */
    List<UserInfo> findAll();

    /**
     * 根据用户id查询用户地址列表
     * @param userId
     * @return
     */
    List<UserAddress>getUserAddressList(String userId);

    //登陆方法
    UserInfo login(UserInfo userInfo);

    //根据用户Id查询数据
    UserInfo verify(String userId);
}
