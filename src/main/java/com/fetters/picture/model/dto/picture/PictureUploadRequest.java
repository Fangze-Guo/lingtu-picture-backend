package com.fetters.picture.model.dto.picture;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 图片上传请求
 * @author Fetters
 */
@Data
public class PictureUploadRequest implements Serializable {

    /**
     * 图片 id（用于修改）
     */
    private Long id;

    /**
     * 空间 id
     */
    private Long spaceId;

    /**
     * 文件地址（用于 URL 上传）
     */
    private String fileUrl;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 分类
     */
    private String category;

    /**
     * 标签
     */
    private List<String> tags;

    private static final long serialVersionUID = 1L;
}
