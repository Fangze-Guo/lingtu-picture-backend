package com.fetters.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fetters.picture.common.DeleteRequest;
import com.fetters.picture.model.dto.space.SpaceAddRequest;
import com.fetters.picture.model.dto.space.SpaceQueryRequest;
import com.fetters.picture.model.entity.Space;
import com.fetters.picture.model.entity.User;
import com.fetters.picture.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 99716
 * @description 针对表【space(空间)】的数据库操作Service
 * @createDate 2025-06-13 11:55:30
 */
public interface SpaceService extends IService<Space> {

    /**
     * 添加空间
     * @param spaceAddRequest 添加空间请求
     * @param loginUser       登录用户
     * @return 添加的空间ID
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 获取处理后的查询条件
     * @param spaceQueryRequest 空间查询条件
     * @return 封装后的查询条件（分页）
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 获取空间封装类
     * @param space   空间
     * @param request 请求
     * @return 空间封装类
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 分页获取空间封装
     * @param spacePage 空间分页
     * @param request   请求
     * @return 空间封装分页
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 校验空间
     * @param space 空间
     * @param add   是否为创建时校验
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别填充空间信息
     * @param space 空间
     */
    void fillSpaceBySpaceLevel(Space space);

    /**
     * 删除空间和图片
     * @param deleteRequest 删除请求
     * @param loginUser     登录用户
     */
    void deleteSpaceAndPictures(DeleteRequest deleteRequest, User loginUser);
}
