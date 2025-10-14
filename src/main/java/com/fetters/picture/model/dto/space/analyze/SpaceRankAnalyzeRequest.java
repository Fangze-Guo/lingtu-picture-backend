package com.fetters.picture.model.dto.space.analyze;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 空间使用排名分析请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SpaceRankAnalyzeRequest extends SpaceAnalyzeRequest {

    /**
     * 排名前 N 的空间
     */
    private Integer topN = 10;
}
