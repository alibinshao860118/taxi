package com.alibinshao.controller;

import com.alibinshao.constants.TokenConstants;
import com.alibinshao.result.ResponseResult;
import com.alibinshao.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liangbinbin
 * @title: TestController
 * @projectName
 * @description:
 * @date 2024/5/8
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping("test/auth/error")
    public ResponseResult error(@RequestParam(required = false) String code){

        return ResponseResult.error("测试异常请求");
    }


    @GetMapping("/test/auth/login")
    public ResponseResult loginV2(@RequestParam(required = false) String code, @RequestParam(required = false) String state,
                        HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("iam web 认证登录：{}",code);
        /*IamUserInfo iamToken = iamService.getUserInfo(code);
        if(!iamToken.getCode().equals(0)){
            responseError(response,iamToken.getMessage());
            return;
        }
        log.info("iam web 认证成功，userInfo：{}", JSONUtil.toJsonStr(iamToken));
        String account = iamToken.getLoginName();
        if(account.startsWith("97")&&account.length()==9){ // 临时账号取employeeNumber
            account = iamToken.getEmployeeNumber();
        }
        log.info("iam web 认证成功，识别员工号：{}",account);
        log.info("ehome sso 跳转url：{}",state);
        if(isNotCsairHost(state)){
            responseError(response,PATH_ERROR);
            return;
        }*/
//        ssoValidate.loadUser(request.getSession().getId(),account);
        Cookie cookie = new Cookie(TokenConstants.LOGIN_TOKEN_KEY, request.getSession().getId());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        log.info("ehome login forward：{}",state);
        response.sendRedirect(state);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId","code");
        String token = JwtUtils.createToken(claims);
        return ResponseResult.success(token);
    }

    /**
     * 直接输出信息
     */
    public void responseError(HttpServletResponse response,String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String format = String.format("异常",message);
        response.getWriter().write(format);
    }





}
