package com.fetters.picture.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

/**
 * @author : Fetters
 * @description : 图片审核状态枚举
 * @createDate : 2025/6/6 11:44
 */
@Getter
public enum PictureReviewStatusEnum {

    REVIEW_PENDING("待审核", 0),
    REVIEW_PASS("审核通过", 1),
    REVIEW_REJECT("审核不通过", 2);

    private final String text;
    private final int value;

    PictureReviewStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     * @param value
     * @return
     */
    public static PictureReviewStatusEnum getEnumByValue(Integer value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (PictureReviewStatusEnum pictureReviewStatusEnum : PictureReviewStatusEnum.values()) {
            if (pictureReviewStatusEnum.value == value) {
                return pictureReviewStatusEnum;
            }
        }
        return null;
    }
}
