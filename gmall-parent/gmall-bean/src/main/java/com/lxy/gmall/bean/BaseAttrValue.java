package com.lxy.gmall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-11 21:08
 */

@Data
public class BaseAttrValue implements Serializable {
    @Id
    @Column
    private String id;
    @Column
    private String valueName;
    @Column
    private String attrId;

    //声明一个变量
    @Transient
    private String urlParam;

}
