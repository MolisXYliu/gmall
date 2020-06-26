package com.lxy.gmall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-13 0:09
 */

@Data
public class BaseSaleAttr implements Serializable {
    @Id
    @Column
    String id ;

    @Column
    String name;

}
