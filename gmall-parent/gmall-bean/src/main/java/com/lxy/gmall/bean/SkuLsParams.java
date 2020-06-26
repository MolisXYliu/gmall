package com.lxy.gmall.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-16 19:57
 */

@Data
public class SkuLsParams implements Serializable {

    //keyword=skuName
    String  keyword;

    String catalog3Id;

    String[] valueId;

    int pageNo=1;

    int pageSize=20;

}
