package com.lxy.gmall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-12 14:57
 */

@Data
public class SpuInfo implements Serializable {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String spuName;

    @Column
    private String description;

    @Column
    private  String catalog3Id;

    //销售属性集合
    @Transient
    private List<SpuSaleAttr> spuSaleAttrList;

    //图片列表集合
    @Transient
    private List<SpuImage> spuImageList;



}
