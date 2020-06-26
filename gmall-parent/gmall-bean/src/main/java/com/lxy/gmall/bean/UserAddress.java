package com.lxy.gmall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-10 10:08
 */

@Data
public class UserAddress implements Serializable {
    @Column
    @Id
    private String id;
    @Column
    private String userAddress;
    @Column
    private String userId;
    @Column
    private String consignee;
    @Column
    private String phoneNum;
    @Column
    private String isDefault;


}
