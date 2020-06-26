package com.lxy.gmall.service;

import com.lxy.gmall.bean.*;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-11 21:19
 */
public interface ManageService {

    //获取所有的一级分类数据
    List<BaseCatalog1>getCatalog1();

    //根据一级分类id查询二级分类数据
    //select * from baseCatalog2 where catalog1Id=?
    List<BaseCatalog2>getCatalog2(String catalog1Id);

    //根据二级分类id查询三级分类数据
    //select * from baseCatalog3 where catalog2Id=?
    List<BaseCatalog3>getCatalog3(String catalog2Id);

    //根据三级分类Id查询平台属性集合
    List<BaseAttrInfo>getAttrList(String catalog3Id);

    //保存平台属性数据
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    //根据平台属性ID查询平台属性值集合
    List<BaseAttrValue> getAttrValueList(String attrId);

    //根据平台属性ID查询平台属性对象
    BaseAttrInfo getAttrInfo(String attrId);

    //List<SpuInfo>getSpuList(String catalogId);

    //根据SpuInfo对象属性获取spuInfo集合
    List<SpuInfo>getSpuList(SpuInfo spuInfo);

    //获取所有的销售属性
    List<BaseSaleAttr>getBaseSaleAttrList();

    //保存spuInfo
    void saveSpuInfo(SpuInfo spuInfo);

    //
    List<SpuImage> getSpuImageList(SpuImage spuImage);

    //根据spuId获取销售属性集合
    List<SpuSaleAttr> getSpuAttrList(String spuId);

    //保存skuInfo数据
    void saveSkuInfo(SkuInfo skuInfo);

    //根据skuId查询skuInfo信息
    SkuInfo getSkuInfo(String skuId);

    //根据skuId查询图片信息
    List<SkuImage> getSkuImageBySkuId(String skuId);

    //根据skuId spuId查询销售属性集合
    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(SkuInfo skuInfo);

    //根据spuId查询销售属性值集合
    List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId);

    //根据平台属性值Id查询
    List<BaseAttrInfo> getAttrList(List<String> attrValueIdList);
}
