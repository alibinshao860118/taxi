package com.alibinshao.taxiclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TaxiClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiClientApplication.class, args);
    }

}
