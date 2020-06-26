package com.lxy.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lxy.gmall.bean.SkuImage;
import com.lxy.gmall.bean.SkuInfo;
import com.lxy.gmall.bean.SkuSaleAttrValue;
import com.lxy.gmall.bean.SpuSaleAttr;
import com.lxy.gmall.config.LoginRequire;
import com.lxy.gmall.service.ListService;
import com.lxy.gmall.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-14 16:20
 */

@Controller
public class ItemController {


    @Reference
    private ManageService manageService;

    @Reference
    private ListService listService;

    //控制器
    @RequestMapping("{skuId}.html")
    //@LoginRequire//用户在访问商品详情的时候必须登录
    public String item(@PathVariable String skuId,HttpServletRequest request){
        //根据skuId获取数据
        SkuInfo skuInfo=manageService.getSkuInfo(skuId);
        //显示图片列表
        //根据skuId skuImage中
        //List<SkuImage> skuImageList=manageService.getSkuImageBySkuId(skuId);
        //将图片集合保存到作用域中
        //request.setAttribute("skuImageList",skuImageList);

        //查询销售属性和销售属性值集合 spuId skuId
        List<SpuSaleAttr> spuSaleAttrList=manageService.getSpuSaleAttrListCheckBySku(skuInfo);

        //获取销售属性值Id
        List<SkuSaleAttrValue>skuSaleAttrValueList= manageService.getSkuSaleAttrValueListBySpu(skuInfo.getSpuId());
        //遍历集合拼接字符串
        //增强for循环
        //将数据放入map中，将map转换为想要的json格式！
        //map.put("118|120","33") JSON.toJSONString(map)
     /*   for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {


        }*/
        System.out.println(skuSaleAttrValueList.toString());
        String key="";
        HashMap<String, Object> map = new HashMap<>();
        //普通循环
        for (int i = 0; i < skuSaleAttrValueList.size(); i++) {
            SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueList.get(i);
            //什么时候停止拼接？当本次循环的skuId与下次循环的skuId不一致的时候，停止拼接。 拼接到最后则停止拼接！
            if(key.length()>0){
                key+="|";
            }
            key += skuSaleAttrValue.getSaleAttrValueId();
            if((i+1)==skuSaleAttrValueList.size() ||!skuSaleAttrValue.getSkuId().equals(skuSaleAttrValueList.get(i+1).getSkuId())){
                //放入map集合
                map.put(key, skuSaleAttrValue.getSkuId());
                //清空key
                key="";
            }
        }
        //将map转换为json字符串
        String valueSkuJson = JSON.toJSONString(map);
        System.out.println("拼接的json：="+valueSkuJson);
        //保存json
        request.setAttribute("valueSkuJson",valueSkuJson);



        request.setAttribute("spuSaleAttrList",spuSaleAttrList);
        //保存到作用域
        request.setAttribute("skuInfo",skuInfo);

        listService.incrHotScore(skuId);
        return "item";
    }





}
