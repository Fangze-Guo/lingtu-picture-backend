package com.fetters.picture.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.fetters.picture.config.OssConfig;
import com.fetters.picture.exception.BusinessException;
import com.fetters.picture.exception.ErrorCode;
import com.fetters.picture.exception.ThrowUtils;
import com.fetters.picture.model.dto.file.UploadPictureResult;
import com.fetters.picture.model.entity.ImageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文件管理服务
 * @deprecated 已废弃，改用 upload 包的模板方法优化
 */
@Service
@Slf4j
@Deprecated
public class FileManager {

    @Resource
    private OssManager ossManager;

    @Resource
    private OssConfig ossConfig;

    /**
     * 上传文件到 OSS
     * @param multipartFile    文件
     * @param uploadPathPrefix 上传路径前缀
     * @return 文件访问 URL
     */
    public UploadPictureResult uploadFile(MultipartFile multipartFile, String uploadPathPrefix) {
        try {
            // 校验图片
            validPicture(multipartFile);

            // 图片上传地址
            String uuid = RandomUtil.randomString(16);
            String originalFilename = multipartFile.getOriginalFilename();
            String uploadFilename = String.format("%s_%s.%s",
                    DateUtil.formatDate(new Date()), uuid, FileUtil.getSuffix(originalFilename));
            String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFilename);

            // 将 MultipartFile 转换为 File
            File file = convertToFile(multipartFile);

            // 使用 OssManager 封装的上传方法
            ossManager.putObject(uploadPath, file);

            // 获取图片信息
            ImageInfo imageInfo = ossManager.getImageObject(uploadPath);
            int picWidth = imageInfo.getWidth();
            int picHeight = imageInfo.getHeight();
            double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
            String picFormat = imageInfo.getFormat();

            // 封装返回结果
            UploadPictureResult uploadPictureResult = new UploadPictureResult();
            uploadPictureResult.setUrl("https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/" + uploadPath);
            uploadPictureResult.setPicName(FileUtil.mainName(originalFilename));
            uploadPictureResult.setPicSize(FileUtil.size(file));
            uploadPictureResult.setPicWidth(picWidth);
            uploadPictureResult.setPicHeight(picHeight);
            uploadPictureResult.setPicScale(picScale);
            uploadPictureResult.setPicFormat(picFormat);
            return uploadPictureResult;
        } catch (Exception e) {
            log.error("图片上传到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传文件失败");
        }
    }

    /**
     * 下载文件
     * @param key      OSS 中的文件名
     * @param response HTTP 响应对象，用于输出文件流
     */
    public void downloadFile(String key, HttpServletResponse response) {
        try {
            // 从OSS中获取文件输入流
            InputStream objectContent = ossManager.getObject(key).getObjectContent();

            // 设置响应内容类型和文件名
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + key);

            // 将输入流中的数据写入响应输出流
            response.getOutputStream().write(objectContent.readAllBytes());
            response.getOutputStream().flush();
        } catch (Exception e) {
            // 记录错误日志
            log.error("下载文件失败", e);
            // 抛出业务异常，表示下载文件失败
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "下载文件失败");
        }
    }

    /**
     * 校验图片
     */
    private void validPicture(MultipartFile multipartFile) {
        ThrowUtils.throwIf(multipartFile.isEmpty(), ErrorCode.PARAMS_ERROR, "文件不能为空");
        // 1.校验文件大小
        long fileSize = multipartFile.getSize();
        ThrowUtils.throwIf(fileSize > 1024 * 1024 * 20, ErrorCode.PARAMS_ERROR, "文件大小不能超过 20MB");
        // 2.校验文件类型
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final List<String> ALLOW_FORMAT_LIST = Arrays.asList("jpg", "jpeg", "png", "gif");
        ThrowUtils.throwIf(!ALLOW_FORMAT_LIST.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "不支持的文件格式");
    }

    /**
     * 将Spring框架的MultipartFile对象转换为java.io.File对象
     * 此方法主要用于处理文件上传时的临时文件转换，便于后续处理
     * @param multipartFile Spring框架中的MultipartFile对象，代表上传的文件
     * @return 转换后的java.io.File对象
     * @throws IOException 文件转换过程中可能抛出的异常
     */
    private File convertToFile(MultipartFile multipartFile) throws IOException {
        // 创建一个临时文件，用于存储上传的文件内容
        // 前缀"upload-"用于标识这是一个上传的文件，后缀".tmp"表示这是一个临时文件
        File tempFile = File.createTempFile("upload-", ".tmp");

        // 确保在程序退出时删除临时文件，以避免磁盘空间泄漏
        tempFile.deleteOnExit();

        // 将上传的文件内容转移到临时文件中
        multipartFile.transferTo(tempFile);

        // 返回转换后的临时文件对象
        return tempFile;
    }
}
