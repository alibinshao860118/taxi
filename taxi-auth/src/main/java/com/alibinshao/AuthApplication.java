package com.alibinshao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author lbb
 * @description: 鉴权中心启动类
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("鉴权中心项目启动完成！");
            // 在这里可以执行其他的启动任务
        };
    }

}