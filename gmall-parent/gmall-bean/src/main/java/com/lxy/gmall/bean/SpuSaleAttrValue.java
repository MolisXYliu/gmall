package com.lxy.gmall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-13 8:27
 */

@Data
public class SpuSaleAttrValue implements Serializable {
    @Id
    @Column
    String id ;

    @Column
    String spuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrValueName;

    //当前的属性值是否被选中
    @Transient
    String isChecked;

}
