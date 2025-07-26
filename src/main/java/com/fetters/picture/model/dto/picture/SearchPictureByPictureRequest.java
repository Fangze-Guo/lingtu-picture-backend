package com.fetters.picture.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Fetters
 * @description : 以图搜图请求
 * @createDate : 2025/7/26 22:00
 */
@Data
public class SearchPictureByPictureRequest implements Serializable {

    /**
     * 图片 id
     */
    private Long pictureId;

    private static final long serialVersionUID = 1L;
}


