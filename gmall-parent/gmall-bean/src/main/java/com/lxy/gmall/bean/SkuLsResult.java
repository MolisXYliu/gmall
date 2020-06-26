package com.lxy.gmall.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-16 20:00
 */

@Data
public class SkuLsResult implements Serializable {

    List<SkuLsInfo> skuLsInfoList;

    long total;

    long totalPages;

    //平台属性值Id集合
    List<String> attrValueIdList;

}
