package com.lxy.gmall.bean;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-14 8:46
 */
@Data
@ToString
public class SkuSaleAttrValue implements Serializable {

    @Id
    @Column
    String id;

    @Column
    String skuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrValueId;

    @Column
    String saleAttrName;

    @Column
    String saleAttrValueName;

}
