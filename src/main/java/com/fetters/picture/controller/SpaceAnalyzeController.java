package com.fetters.picture.controller;

import com.fetters.picture.common.BaseResponse;
import com.fetters.picture.common.ResultUtils;
import com.fetters.picture.exception.ErrorCode;
import com.fetters.picture.exception.ThrowUtils;
import com.fetters.picture.model.dto.space.analyze.SpaceCategoryAnalyzeRequest;
import com.fetters.picture.model.dto.space.analyze.SpaceTagAnalyzeRequest;
import com.fetters.picture.model.dto.space.analyze.SpaceUsageAnalyzeRequest;
import com.fetters.picture.model.entity.User;
import com.fetters.picture.model.vo.space.analyze.SpaceCategoryAnalyzeResponse;
import com.fetters.picture.model.vo.space.analyze.SpaceTagAnalyzeResponse;
import com.fetters.picture.model.vo.space.analyze.SpaceUsageAnalyzeResponse;
import com.fetters.picture.service.SpaceAnalyzeService;
import com.fetters.picture.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : Fetters
 * @description : 空间图片分析接口
 * @createDate : 2025/10/12 19:46
 */
@RestController
@RequestMapping("/space/analyze")
public class SpaceAnalyzeController {

    @Resource
    private UserService userService;

    @Resource
    private SpaceAnalyzeService spaceAnalyzeService;

    /**
     * 获取空间图片使用分析数据接口
     */
    @PostMapping("/usage")
    public BaseResponse<SpaceUsageAnalyzeResponse> getSpaceUsageAnalyze(
            @RequestBody SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest,
            HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        SpaceUsageAnalyzeResponse spaceUsageAnalyze = spaceAnalyzeService.getSpaceUsageAnalyze(spaceUsageAnalyzeRequest, loginUser);
        return ResultUtils.success(spaceUsageAnalyze);
    }

    /**
     * 获取空间图片分类分析数据接口
     */
    @PostMapping("/category")
    public BaseResponse<List<SpaceCategoryAnalyzeResponse>> getSpaceCategoryAnalyze(@RequestBody SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(spaceCategoryAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        List<SpaceCategoryAnalyzeResponse> resultList = spaceAnalyzeService.getSpaceCategoryAnalyze(spaceCategoryAnalyzeRequest, loginUser);
        return ResultUtils.success(resultList);
    }

    /**
     * 获取空间图片标签分析数据接口
     */
    @PostMapping("/tag")
    public BaseResponse<List<SpaceTagAnalyzeResponse>> getSpaceTagAnalyze(@RequestBody SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(spaceTagAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        List<SpaceTagAnalyzeResponse> resultList = spaceAnalyzeService.getSpaceTagAnalyze(spaceTagAnalyzeRequest, loginUser);
        return ResultUtils.success(resultList);
    }
}

