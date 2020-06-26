package com.lxy.gmall.manager.controller;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-12 23:48
 */

@RestController
@CrossOrigin
public class FileUploadController {

    //value注解使用的前提条件是当前类必须在spring容器中
    @Value("${fileServer.url}")
    private String fileUrl;//fileUrl=http://192.168.119.133;

    //服务器的ip地址作为一个配置文件放入项目中！软编码！
    //http://localhost:8082/fileUpload
    //获取上传文件需要使用spring MVC技术
    @RequestMapping("fileUpload")
    public String fileUpload(MultipartFile file) throws IOException, MyException {
        String imgUrl=fileUrl;
        //当文件不为空的时候，进行上传
        if(file!=null){
            String configFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(configFile);
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            StorageClient storageClient=new StorageClient(trackerServer,null);
            //获取上传文件名称
            String originalFilename = file.getOriginalFilename();
            //获取文件的后缀名
            String extName = StringUtils.substringAfterLast(originalFilename, ".");
            //String orginalFilename="d://p1.jpg";
            //String[] upload_file = storageClient.upload_file(originalFilename, extName, null);//获取本地文件
            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
            for (int i = 0; i < upload_file.length; i++) {
                String path = upload_file[i];
                imgUrl +="/"+path;
            }
        }
            return imgUrl;
    }


}
