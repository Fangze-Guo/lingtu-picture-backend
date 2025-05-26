package com.fetters.picture.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Fetters
 * @description : 用户修改请求
 * @createDate : 2025/5/26 16:11
 */
@Data
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 8465300000465472521L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;
}

