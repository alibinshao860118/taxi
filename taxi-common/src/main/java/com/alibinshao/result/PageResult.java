package com.alibinshao.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lbb
 * @description: 列表返回结果集
 */
@Data
public class PageResult<T> implements Serializable {
    /**
     * 总条数
     */
    private long total;
    /**
     * 结果集合
     */
    private List<T> list;
    public PageResult() {
    }
    public PageResult(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }
    public static <T> PageResult<T> toPageResult(long total, List<T> list){
        return new PageResult(total , list);
    }
    public static <T> ResponseResult<PageResult<T>> toResult(long total, List<T> list){
        return ResponseResult.success(PageResult.toPageResult(total,list));
    }
}