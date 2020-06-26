package com.lxy.gmall.service;

import com.lxy.gmall.bean.SkuLsInfo;
import com.lxy.gmall.bean.SkuLsParams;
import com.lxy.gmall.bean.SkuLsResult;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-16 15:47
 */

public interface ListService {

    //保存数据到es中
    void saveSkuLsInfo(SkuLsInfo skuLsInfo);

    //检索数据
    SkuLsResult search(SkuLsParams skuLsParams);

    //记录每个商品被访问的次数
    void incrHotScore(String skuId);
}
