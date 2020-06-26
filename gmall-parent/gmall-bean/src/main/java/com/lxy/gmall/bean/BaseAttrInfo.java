package com.lxy.gmall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-11 21:07
 */

@Data
public class BaseAttrInfo implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)//获取主键自增
    private String id;
    @Column
    private String attrName;
    @Column
    private String catalog3Id;

    //BaseAttrValue的集合
    @Transient//表示当前字段不是表中的字段 是业务需要使用
    private List<BaseAttrValue> attrValueList;


}
