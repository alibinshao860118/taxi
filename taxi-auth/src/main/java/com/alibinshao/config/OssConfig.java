package com.alibinshao.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {
    @Value("${aliyun.oss.endpoint}")
    private String endPoint;
    @Value("${aliyun.oss.AccessKey_ID}")
    private String AccessKey_ID;
    @Value("${aliyun.oss.AccessKey_Secret}")
    private String AccessKey_Secret;

    @Value("${aliyun.oss.bucket_endpoint}")
    private String bucketEndpoint;

    @Bean
    public OSS ossClient(){
        // Endpoint以成都为例。
        //创建对象存储的域名
//        String endpoint = "创建对象存储的域名";
        //验证码获取的accessKeyId
//        String accessKeyId = "验证码获取的accessKeyId";
        //验证码获取的accessKeySecret
//        String accessKeySecret = "验证码获取的accessKeySecret";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(bucketEndpoint, AccessKey_ID, AccessKey_Secret);
        return ossClient;
    }
}