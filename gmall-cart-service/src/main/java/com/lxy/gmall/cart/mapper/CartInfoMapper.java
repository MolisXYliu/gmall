package com.lxy.gmall.cart.mapper;

import com.lxy.gmall.bean.CartInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-19 21:43
 */

public interface CartInfoMapper extends Mapper<CartInfo> {

    //根据userId查询实时价格 到cartInfo中
    List<CartInfo> selectCartListWithCurPrice(String userId);
}
