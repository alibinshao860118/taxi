package com.alibinshao.cloudeureka.listener;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 监听下线事件
 * @Author:liangbinbin
 * @Date:2021-07-27
 * @Description:
 * @Description:
 * @version:1.0
 */
@Component
public class TestEvent {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event){
        //TODO 发邮件、短信等
        System.out.println(event.getServerId());
    }
}
