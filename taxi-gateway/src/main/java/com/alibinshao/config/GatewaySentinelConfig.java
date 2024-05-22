package com.alibinshao.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lbb
 * @deprecation: 网关限流控件
 */
@Configuration
public class GatewaySentinelConfig {
    /**
     * 查看解析器
     */
    private final List<ViewResolver> viewResolvers;
    /**
     * 服务器编解码器配置
     */
    private final ServerCodecConfigurer serverCodecConfigurer;
    public GatewaySentinelConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                 ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }
    /**
     * Sentinel 网关块异常处理程序
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // 给 Spring Cloud Gateway 注册块异常处理程序。
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }
 
    /**
     * 初始化网关配置
     */
    @PostConstruct
    public void doInit() {
        initGatewayRules();
    }
    /**
     * 配置限流规则
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule("cloud-user")
            // 限流阈值
            .setCount(1)
            // 统计时间窗口，单位是秒，默认是 1 秒
            .setIntervalSec(5)
        );
        //添加到限流规则当中
        GatewayRuleManager.loadRules(rules);
    }
}