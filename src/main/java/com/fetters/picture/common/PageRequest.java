package com.fetters.picture.common;

import lombok.Data;

/**
 * @author : Fetters
 * @description : 通用分页请求类
 * @createDate : 2025/5/10 16:14
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认降序）
     */
    private String sortOrder = "descend";
}

