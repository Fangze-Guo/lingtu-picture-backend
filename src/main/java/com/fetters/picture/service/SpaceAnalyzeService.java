package com.fetters.picture.service;

import com.fetters.picture.model.dto.space.analyze.*;
import com.fetters.picture.model.entity.Space;
import com.fetters.picture.model.entity.User;
import com.fetters.picture.model.vo.space.analyze.*;

import java.util.List;

/**
 * @author : Fetters
 * @description : 空间分析服务
 * @createDate : 2025/10/12 19:32
 */
public interface SpaceAnalyzeService {

    /**
     * 获取空间使用分析数据
     * @param spaceUsageAnalyzeRequest SpaceUsageAnalyzeRequest 请求参数
     * @param loginUser                当前登录用户
     * @return SpaceUsageAnalyzeResponse 分析结果
     */
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);

    /**
     * 获取空间分类分析数据
     * @param spaceCategoryAnalyzeRequest 空间分类分析请求参数
     * @param loginUser                   当前登录用户
     * @return List<SpaceCategoryAnalyzeResponse> 空间分类分析结果响应列表
     */
    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser);

    /**
     * 获取空间图片图片标签分析数据
     * @param spaceTagAnalyzeRequest 空间图片标签分析请求参数
     * @param loginUser              当前登录用户
     * @return List<SpaceTagAnalyzeResponse> 空间图片标签分析结果响应列表
     */
    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser);

    /**
     * 获取空间图片大小分析数据
     * @param spaceSizeAnalyzeRequest 空间图片大小分析请求参数
     * @param loginUser               当前登录用户
     * @return List<SpaceSizeAnalyzeResponse> 空间图片大小分析结果响应列表
     */
    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser);

    /**
     * 获取空间用户上传行为分析数据
     * @param spaceUserAnalyzeRequest 空间用户上传行为分析请求参数
     * @param loginUser               当前登录用户
     * @return List<SpaceUserAnalyzeResponse> 空间用户上传行为分析结果响应列表
     */
    List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest, User loginUser);

    /**
     * 获取空间使用排行数据（管理员）
     * @param spaceRankAnalyzeRequest 空间使用排行数据请求参数
     * @param loginUser               当前登录用户
     * @return List<Space> 空间列表
     */
    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest, User loginUser);
}
