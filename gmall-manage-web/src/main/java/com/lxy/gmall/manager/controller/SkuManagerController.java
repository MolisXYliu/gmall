package com.lxy.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.lxy.gmall.bean.SkuInfo;
import com.lxy.gmall.bean.SkuLsInfo;
import com.lxy.gmall.bean.SpuImage;
import com.lxy.gmall.bean.SpuSaleAttr;
import com.lxy.gmall.service.ListService;
import com.lxy.gmall.service.ManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-13 17:14
 */

@RestController
@CrossOrigin
public class SkuManagerController {

    //http://localhost:8082/spuImageList?spuId=58
/*    @RequestMapping("spuImageList")
    public List<SpuImage>spuImageList(String spuId){

    }*/

    @Reference
    private ManageService manageService;

    @Reference
    private ListService listService;

    @RequestMapping("spuImageList")
    public List<SpuImage>spuImageList(SpuImage spuImage){
        //调用service层
        return manageService.getSpuImageList(spuImage);
    }

    //http://localhost:8082/spuSaleAttrList?spuId=59
    @RequestMapping("spuSaleAttrList")
    List<SpuSaleAttr>spuSaleAttrList(String spuId){
        //调用service层
       return manageService.getSpuAttrList(spuId);
    }

    //http://localhost:8082/saveSkuInfo
    @RequestMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody SkuInfo skuInfo){
        if(skuInfo!=null){

            manageService.saveSkuInfo(skuInfo);
        }
    }

    //上传一个商品，如果上传批量！
    @RequestMapping("onSale")
    public void onSale(String skuId){
        //创建一个skuLsInfo对象
        SkuLsInfo skuLsInfo = new SkuLsInfo();
        //给当前skuLsInfo赋值
        SkuInfo skuInfo = manageService.getSkuInfo(skuId);
       /* System.out.println("skuId="+skuId);
        System.out.println("skuInfo="+skuInfo);*/
        //属性拷贝
        BeanUtils.copyProperties(skuInfo, skuLsInfo);
        /*System.out.println("skuLsInfo="+skuLsInfo);*/
        listService.saveSkuLsInfo(skuLsInfo);
    }

}
