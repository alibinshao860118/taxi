package com.alibinshao.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.Claims;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.alibinshao.constants.JwtConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author muyu
 * @description: Jwt工具类
 */
@Component
@Slf4j
public class JwtUtils {
    /**
     * 会话超时秒数
     */
    public static int SESSION_TIMEOUT = 3600;

    public static String secret = JwtConstants.SECRET;
    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims){
        String userId = claims.get("userId").toString();
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("uid", CommonUtil.uuid());
                put("expire_time", System.currentTimeMillis() + 1000 * SESSION_TIMEOUT);
                put("userId",userId);
            }
        };
        return JWTUtil.createToken(map, secret.getBytes());
    }
    /**
     * 从令牌中获取数据声明
     * @param rightToken 令牌
     * @return 数据声明
     */
    public static JSONObject parseToken(String rightToken){
        final JWT jwt = JWTUtil.parseToken(rightToken);

        jwt.getHeader(JWTHeader.TYPE);
        JSONObject pay = jwt.getPayloads();
        return pay;
    }

    /**
     * 校验token
     * @param rightToken
     * @return
     */
    public boolean checkToken(String rightToken) {
        if (StrUtil.isNotEmpty(rightToken)){
            try {
                return JWTUtil.verify(rightToken, secret.getBytes());
            } catch (Exception e) {
                log.error("校验token异常：{}",e.getMessage());
                return false;
            }
        }
        return false;
    }
    /**
     * 根据令牌获取用户标识
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserKey(String token){
        JSONObject claims = parseToken(token);
        return getValue(claims, JwtConstants.USER_KEY);
    }
    /**
     * 根据令牌获取用户标识
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserKey(JSONObject claims){
        return getValue(claims, JwtConstants.USER_KEY);
    }
    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserId(String token){
        JSONObject claims = parseToken(token);
        return getValue(claims, JwtConstants.DETAILS_USER_ID);
    }
    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserId(JSONObject claims){
        return getValue(claims, JwtConstants.DETAILS_USER_ID);
    }
    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUserName(String token){
        JSONObject claims = parseToken(token);
        return getValue(claims, JwtConstants.DETAILS_USERNAME);
    }
    /**
     * 根据身份信息获取用户名
     *
     * @param claims 身份信息
     * @return 用户名
     */
    public static String getUserName(JSONObject claims){
        return getValue(claims, JwtConstants.DETAILS_USERNAME);
    }
    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key 键
     * @return 值
     */
    public static String getValue(JSONObject claims, String key){
        Object obj = claims.get(key);
        return obj == null ? "" : obj.toString();
    }
}