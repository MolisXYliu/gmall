package com.lxy.gmall.manage.mapper;

import com.lxy.gmall.bean.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-13 15:08
 */
public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {
    //根据spuId查询销售属性集合
    //SpuSaleAttrMapper.xml 写在resources目录下
    List<SpuSaleAttr> selectSpuSaleAttrList(String spuId);

    //
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(String skuId, String spuId);
}
