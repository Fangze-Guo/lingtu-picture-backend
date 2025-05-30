package com.fetters.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fetters.picture.model.dto.user.UserQueryRequest;
import com.fetters.picture.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fetters.picture.model.vo.LoginUserVO;
import com.fetters.picture.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;

/**
* @author Fetters
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-05-24 10:55:26
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取用户脱敏信息
     * @param user
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取加密后的密码
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏后的用户信息
     * @param user 用户信息
     * @return 脱敏后的用户信息
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏后的用户列表
     * @param userList 用户列表
     * @return 脱敏后的用户列表
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 获取处理后的查询条件
     * @param userQueryRequest 用户查询条件
     * @return 封装后的查询条件（分页）
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 判断用户是否为管理员
     * @param user
     * @return
     */
    boolean isAdmin(User user);
}
