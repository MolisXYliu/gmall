package com.lxy.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lxy.gmall.bean.SpuInfo;
import com.lxy.gmall.service.ManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-12 15:05
 */

@RestController
@CrossOrigin
public class SpuManagerController {


    @Reference
    ManageService manageService;

    //http://localhost:8082/spuList?catalog3Id=1 实体类对象封装
    @RequestMapping("spuList")
    public List<SpuInfo>spuList(SpuInfo spuInfo){
       return manageService.getSpuList(spuInfo);
    }


    //http://localhost:8082/saveSpuInfo
    @RequestMapping("saveSpuInfo")
    public void saveSpuInfo(@RequestBody SpuInfo spuInfo){
        if(spuInfo!=null){
            //调用保存
            manageService.saveSpuInfo(spuInfo);
        }

    }

}
