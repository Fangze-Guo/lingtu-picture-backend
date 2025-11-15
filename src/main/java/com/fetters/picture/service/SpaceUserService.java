package com.fetters.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fetters.picture.model.dto.space_user.SpaceUserAddRequest;
import com.fetters.picture.model.dto.space_user.SpaceUserQueryRequest;
import com.fetters.picture.model.entity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fetters.picture.model.vo.space_user.SpaceUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 99716
 * @description 针对表【space_user(空间用户关联)】的数据库操作Service
 * @createDate 2025-11-15 22:11:14
 */
public interface SpaceUserService extends IService<SpaceUser> {

    /**
     * 添加空间用户
     * @param spaceUserAddRequest 团队空间用户添加请求
     * @return
     */
    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    /**
     * 校验空间成员对象
     * @param spaceUser 团队空间用户关联
     * @param add
     */
    void validSpaceUser(SpaceUser spaceUser, boolean add);

    /**
     * 获取查询条件
     * @param spaceUserQueryRequest 团队空间用户查询请求
     * @return
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);

    /**
     * 获取空间用户视图
     * @param spaceUser 团队空间用户关联
     * @param request
     * @return
     */
    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    /**
     * 获取空间用户视图列表
     * @param spaceUserList 团队空间用户关联列表
     * @return
     */
    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);
}
