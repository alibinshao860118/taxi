package com.alibinshao.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibinshao.result.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author muyu
 * @description: 网关处理工具类
 */
@Log4j2
public class GatewayUtils {
    /**
     * 添加请求头参数
     *
     * @param mutate 修改对象
     * @param key    键
     * @param value  值
     */
    public static void addHeader(ServerHttpRequest.Builder mutate, String key, Object value) {
        if (MyStringUtils.isEmpty(key)) {
            log.warn("添加请求头参数键不可以为空");
            return;
        }
        if (value == null) {
            log.warn("添加请求头参数：[{}]值为空", key);
            return;
        }
        String valueStr = value.toString();
        mutate.header(key, valueStr);
        log.info("添加请求头参数成功 - 键:[{}] , 值:[{}]", key, value);
    }

    /**
     * 删除请求头参数
     *
     * @param mutate 修改对象
     * @param key    键
     */
    public static void removeHeader(ServerHttpRequest.Builder mutate, String key) {
        if (MyStringUtils.isEmpty(key)) {
            log.warn("删除请求头参数键不可以为空");
            return;
        }
        mutate.headers(httpHeaders -> httpHeaders.remove(key)).build();
        log.info("删除请求头参数 - 键:[{}]", key);
    }

    /**
     * 错误结果响应
     *
     * @param exchange 响应上下文
     * @param msg      响应消息
     * @return
     */
    public static Mono<Void> errorResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        //设置HTTP响应头状态
        response.setStatusCode(HttpStatus.OK);
        //设置HTTP响应头文本格式
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        //定义响应内容
        ResponseResult<?> result = ResponseResult.error(msg);
        String resultJson = JSONObject.toJSONString(result);
        log.error("[鉴权异常处理]请求路径:[{}]，异常信息：[{}]，响应结果：[{}]", exchange.getRequest().getPath(), msg, resultJson);
        DataBuffer dataBuffer = response.bufferFactory().wrap(resultJson.getBytes());
        //进行响应
        return response.writeWith(Mono.just(dataBuffer));
    }
}