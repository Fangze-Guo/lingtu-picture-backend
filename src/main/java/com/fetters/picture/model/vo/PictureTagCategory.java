package com.fetters.picture.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author : Fetters
 * @description : 图片标签分类列表视图
 * @createDate : 2025/5/30 20:33
 */
@Data
public class PictureTagCategory {
    /**
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 分类列表
     */
    private List<String> categoryList;
}

