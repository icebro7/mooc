package com.cy.controller;

import com.cy.dto.ResponseDTO;
import com.cy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @Autowired
    private AdminService adminService;


    @RequestMapping("fileUpload")
    public ResponseDTO fileUpload(@RequestParam("file") MultipartFile multipartFile , Integer userId) throws IOException {

        //文件保存路径
        String savePath = "D:\\upload";
        //判断文件夹是否存在
        File file = new File(savePath);
        //如果不存在这个文件夹就会创建该文件夹
        if(!file.exists()){
            //创建文件夹
            file.mkdirs();
        }

        //唯一文件名
        String s = UUID.randomUUID().toString().replace("-","");
        //获取原始文件名，切割获取后缀
        String s1 = multipartFile.getOriginalFilename().split("\\.")[1];
        //完整路径（路径+文件名）
        String newPath = savePath + "\\" + s +"." + s1;
        //写出文件
        multipartFile.transferTo(new File(newPath));

        ResponseDTO responseDTO = adminService.updateAdminHeader(userId,s +"." + s1);

        return responseDTO;
    }

}
