package com.alibinshao.controller;

import com.alibinshao.result.ResponseResult;
import com.alibinshao.utils.CommonUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@Slf4j
public class OccController {

    @Autowired
    private OSS ossClient;
    final String bucketName = "aliyun-oss6337";
    @PostMapping("/uploadImg")
    public ResponseResult uploadImg(MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        //获取文件后缀名
        String originalFilename = file.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String png = originalFilename.substring(i);

        //存储空间的名字

        //生成新的文件名 brand/：在bucketName里面创建的分组
        String fileName = "itsource/" + CommonUtil.uuid().substring(0, 4) + png;
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（fileName）。
        PutObjectResult r = ossClient.putObject(bucketName, fileName, file.getInputStream());
        //文件访问地址。
        log.info("getRequestId"+r.getRequestId());
        String url = "https://" + bucketName + ".oss-cn-chengdu.aliyuncs.com" + "/" + fileName;
        System.out.println("上传成功返回的：" + url);
        // 关闭OSSClient。
        ossClient.shutdown();
        return ResponseResult.success(url);
    }
}