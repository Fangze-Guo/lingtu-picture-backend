package com.fetters.picture.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Fetters
 * @description : 通用删除请求类
 * @createDate : 2025/5/10 16:14
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}


