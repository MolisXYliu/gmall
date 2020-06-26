package com.lxy.gmall.manage.mapper;

import com.lxy.gmall.bean.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-14 8:51
 */

public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue> {

    //根据spuId查询数据
    List<SkuSaleAttrValue> selectSkuSaleAttrValueListBySpu(String spuId);
}
