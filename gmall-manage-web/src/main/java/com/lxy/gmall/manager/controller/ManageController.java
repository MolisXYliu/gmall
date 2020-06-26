package com.lxy.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lxy.gmall.bean.*;
import com.lxy.gmall.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-11 2:29
 */

@RestController
@CrossOrigin
public class ManageController {

    @Reference
    private ManageService manageServicel;

    @RequestMapping("getCatalog1")
    public List<BaseCatalog1> getCatalog1(){

        return manageServicel.getCatalog1();
    }

    //http://localhost:8082/getCatalog2?catalog1Id=1
    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> getCatalog2(String catalog1Id){

        return manageServicel.getCatalog2(catalog1Id);
    }


    //http://localhost:8082/getCatalog3?catalog2Id=1
    @RequestMapping("getCatalog3")
    public List<BaseCatalog3> getCatalog3(String catalog2Id){

        return manageServicel.getCatalog3(catalog2Id);
    }

    //http://localhost:8082/attrInfoList?catalog3Id=62
    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(String catalog3Id){

        return manageServicel.getAttrList(catalog3Id);
    }


    //http://localhost:8082/saveAttrInfo
    //将前页面传递过来的json数据转换为对象

    @RequestMapping("saveAttrInfo")
    public void saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        //传递的是
        manageServicel.saveAttrInfo(baseAttrInfo);

    }

    //http://localhost:8082/getAttrValueList?attrId=23
   /* @RequestMapping("getAttrValueList")
    public List<BaseAttrValue>getAttrValueList(String attrId){
        //selet * from baseAttrValue where attrId=?
        return manageServicel.getAttrValueList(attrId);
    }*/

    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue>getAttrValueList(String attrId) {
        //先通过attrId查询平台属性
        BaseAttrInfo baseAttrInfo=manageServicel.getAttrInfo(attrId);
        //返回平台属性中的平台属性值集合
        return baseAttrInfo.getAttrValueList();
    }


    //http://localhost:8082/baseSaleAttrList
    @RequestMapping("baseSaleAttrList")
    public List<BaseSaleAttr>baseSaleAttrList(){
            return manageServicel.getBaseSaleAttrList();

    }


}
