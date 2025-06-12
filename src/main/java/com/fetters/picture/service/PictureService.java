package com.fetters.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fetters.picture.model.dto.picture.PictureQueryRequest;
import com.fetters.picture.model.dto.picture.PictureReviewRequest;
import com.fetters.picture.model.dto.picture.PictureUploadByBatchRequest;
import com.fetters.picture.model.dto.picture.PictureUploadRequest;
import com.fetters.picture.model.entity.Picture;
import com.fetters.picture.model.entity.User;
import com.fetters.picture.model.vo.PictureVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 99716
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-05-29 20:12:46
 */
public interface PictureService extends IService<Picture> {
    /**
     * 上传图片方法
     * @param inputSource          输入源（本地文件或 URL）
     * @param pictureUploadRequest 图片上传请求对象，包含图片ID，用于判断是新增还是更新图片
     * @param loginUser            登录用户信息，用于验证用户权限和确定图片归属
     * @return 返回上传后的图片信息对象
     */
    PictureVO uploadPicture(Object inputSource,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);

    /**
     * 获取处理后的查询条件
     * @param pictureQueryRequest 图片查询条件
     * @return 封装后的查询条件（分页）
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取图片封装类
     * @param picture 图片
     * @param request 请求
     * @return 图片封装类
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 分页获取图片封装
     * @param picturePage 图片分页
     * @param request     请求
     * @return 图片封装分页
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 校验图片
     * @param picture 图片
     */
    void validPicture(Picture picture);

    /**
     * 图片审核
     * @param pictureReviewRequest 图片审核请求
     * @param loginUser            登录用户
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);

    /**
     * 填充审核参数
     * @param picture   图片
     * @param loginUser 登录用户
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 批量上传图片
     * @param pictureUploadByBatchRequest 图片上传批量请求
     * @param loginUser                   登录用户
     * @return 批量上传图片数量
     */
    Integer uploadPictureByBatch(PictureUploadByBatchRequest pictureUploadByBatchRequest, User loginUser);

    /**
     * 清理图片文件
     * @param oldPicture 旧图片
     */
    void clearPictureFile(Picture oldPicture);
}
