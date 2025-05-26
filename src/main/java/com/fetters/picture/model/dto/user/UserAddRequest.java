package com.fetters.picture.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Fetters
 * @description : 用户添加请求
 * @createDate : 2025/5/26 16:09
 */
@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = -3363681754096558649L;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色: user, admin
     */
    private String userRole;
}

