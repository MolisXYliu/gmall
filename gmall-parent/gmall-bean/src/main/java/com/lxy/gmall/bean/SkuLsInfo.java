package com.lxy.gmall.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-16 15:35
 */

@Data
@ToString
public class SkuLsInfo implements Serializable {

    //不加注解因为不是数据库的表
    String id;

    BigDecimal price;

    String skuName;

    String catalog3Id;

    String skuDefaultImg;

    //自定义一个
    Long hotScore=0L;

    List<SkuLsAttrValue> skuAttrValueList;

}
