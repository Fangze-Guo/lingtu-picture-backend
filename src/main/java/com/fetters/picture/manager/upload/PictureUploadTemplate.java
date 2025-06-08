package com.fetters.picture.manager.upload;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.fetters.picture.config.OssConfig;
import com.fetters.picture.exception.BusinessException;
import com.fetters.picture.exception.ErrorCode;
import com.fetters.picture.manager.OssManager;
import com.fetters.picture.model.dto.file.UploadPictureResult;
import com.fetters.picture.model.entity.ImageInfo;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author : Fetters
 * @description : 图片上传模板
 * @createDate : 2025/6/8 10:59
 */
@Slf4j
public abstract class PictureUploadTemplate {

    @Resource
    private OssManager ossManager;

    @Resource
    private OssConfig ossConfig;

    /**
     * 上传图片
     * @param inputSource 输入源（本地文件或 URL）
     * @param uploadPathPrefix 上传路径前缀
     * @return 上传结果
     */
    public UploadPictureResult uploadPicture(Object inputSource, String uploadPathPrefix) {
        // 校验图片
        validPicture(inputSource);
        // 构建上传路径
        String originalFilename = getOriginalFilename(inputSource);
        String uploadPath = buildUploadPath(uploadPathPrefix, originalFilename);
        File file = null;
        try {
            // 创建临时文件，获取文件到服务器
            file = File.createTempFile(uploadPath, null);
            // 处理文件来源
            processFile(inputSource, file);
            // 执行上传并返回结果
            return uploadAndBuildResult(uploadPath, file, FileUtil.mainName(originalFilename));
        } catch (Exception e) {
            log.error("图片上传到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传文件失败");
        } finally {
            if (file != null) {
                file.deleteOnExit();
            }
        }
    }

    /**
     * 校验输入源
     * @param inputSource 输入源（本地文件或 URL）
     */
    protected abstract void validPicture(Object inputSource);

    /**
     * 获取输入源的原始文件名
     * @param inputSource 输入源（本地文件或 URL）
     * @return 原始文件名
     */
    protected abstract String getOriginalFilename(Object inputSource);

    /**
     * 处理文件来源
     * @param inputSource 输入源（本地文件或 URL）
     * @param file 临时文件
     */
    protected abstract void processFile(Object inputSource, File file) throws IOException;

    /**
     * 构建统一的上传路径
     * @param uploadPathPrefix 上传路径前缀
     * @param originalFilename 原始文件名
     * @return 上传路径
     */
    private String buildUploadPath(String uploadPathPrefix, String originalFilename) {
        String uuid = RandomUtil.randomString(16);
        return String.format("%s/%s_%s.%s",
                uploadPathPrefix,
                DateUtil.formatDate(new Date()),
                uuid,
                FileUtil.getSuffix(originalFilename));
    }

    /**
     * 上传并构建结果
     * @param uploadPath 上传路径
     * @param file 临时文件
     * @param picName 图片名称
     * @return 上传结果
     */
    private UploadPictureResult uploadAndBuildResult(String uploadPath, File file, String picName) {
        // 上传文件
        ossManager.putObject(uploadPath, file);

        // 获取图片信息
        ImageInfo imageInfo = ossManager.getImageObject(uploadPath);
        int picWidth = imageInfo.getWidth();
        int picHeight = imageInfo.getHeight();
        double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
        String picFormat = imageInfo.getFormat();

        // 构建返回结果
        UploadPictureResult result = new UploadPictureResult();
        result.setUrl("https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/" + uploadPath);
        result.setPicName(picName);
        result.setPicSize(FileUtil.size(file));
        result.setPicWidth(picWidth);
        result.setPicHeight(picHeight);
        result.setPicScale(picScale);
        result.setPicFormat(picFormat);
        return result;
    }
}

