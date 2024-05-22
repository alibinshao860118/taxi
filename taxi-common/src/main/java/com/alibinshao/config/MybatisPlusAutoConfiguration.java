package com.alibinshao.config;

import cn.hutool.core.util.StrUtil;
import com.alibinshao.utils.MyStringUtils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * @author lbb
 * @version 1.0
 * @date 2024/05/14 14:27
 */
//@ConditionalOnClass(value={PaginationInterceptor.class,MapperScannerConfigurer.class})
@ConditionalOnClass(value={PaginationInnerInterceptor.class,MapperScannerConfigurer.class})
@Configuration
public class MybatisPlusAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /*@Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }*/

    /**
     * 新的分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 相当于顶部的：
     * {@code @MapperScan("com.baomidou.springboot.mapper*")}
     * 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径
     */
    @ConditionalOnProperty(name = "mybatis-plus.mapper-basepackage")
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        String mapping = environment.getProperty("mybatis-plus.mapper-basepackage");
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        if(MyStringUtils.isNotEmpty(mapping)){
            scannerConfigurer.setBasePackage(mapping);
        }
        return scannerConfigurer;
    }
}
