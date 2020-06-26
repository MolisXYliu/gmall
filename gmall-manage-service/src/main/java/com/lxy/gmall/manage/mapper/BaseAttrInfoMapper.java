package com.lxy.gmall.manage.mapper;

import com.lxy.gmall.bean.BaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-11 21:18
 */
public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {
    //根据三级分类ID查询平台属性集合
    List<BaseAttrInfo> getBaseAttrInfoListByCatalog3Id(String catalog3Id);

    //平台属性值Id查询数据
    List<BaseAttrInfo> selectAttrInfoListByIds(@Param("valuIds") String valuIds);
}
