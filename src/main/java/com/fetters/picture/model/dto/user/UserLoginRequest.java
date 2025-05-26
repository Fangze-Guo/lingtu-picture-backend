package com.fetters.picture.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Fetters
 * @description : 用户登录请求
 * @createDate : 2025/5/24 17:01
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 2140066763733263918L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}

