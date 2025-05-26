package com.fetters.picture.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Fetters
 * @description : 用户注册请求
 * @createDate : 2025/5/24 11:09
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 54048753887823929L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 确认密码
     */
    private String checkPassword;
}

