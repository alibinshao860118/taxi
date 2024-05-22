package com.alibinshao.result;

import com.alibinshao.constants.Constants;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liangbinbin
 * @description: 响应信息主体
 */
@Data
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 成功 */
    public static final int SUCCESS = Constants.SUCCESS;
    /** 失败 */
    public static final int FAIL = Constants.ERROR;
    private int code;
    private String msg;
    private T data;
    public static <T> ResponseResult<T> success() {
        return restResult(null, SUCCESS, Constants.SUCCESS_MSG);
    }
    public static <T> ResponseResult<T> success(T data) {
        return restResult(data, SUCCESS, Constants.SUCCESS_MSG);
    }
    public static <T> ResponseResult<T> success(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }
    public static <T> ResponseResult<T> error() {
        return restResult(null, FAIL, Constants.ERROR_MSG);
    }
    public static <T> ResponseResult<T> error(String msg) {
        return restResult(null, FAIL, msg);
    }
    public static <T> ResponseResult<T> error(T data) {
        return restResult(data, FAIL, Constants.ERROR_MSG);
    }
    public static <T> ResponseResult<T> error(T data, String msg) {
        return restResult(data, FAIL, msg);
    }
    public static <T> ResponseResult<T> error(int code, String msg) {
        return restResult(null, code, msg);
    }
    private static <T> ResponseResult<T> restResult(T data, int code, String msg) {
        ResponseResult<T> apiResult = new ResponseResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}