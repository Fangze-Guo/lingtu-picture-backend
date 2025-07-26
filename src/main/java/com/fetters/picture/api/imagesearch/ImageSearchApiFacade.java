package com.fetters.picture.api.imagesearch;

import com.fetters.picture.api.imagesearch.model.ImageSearchResult;
import com.fetters.picture.api.imagesearch.sub.GetImageFirstUrlApi;
import com.fetters.picture.api.imagesearch.sub.GetImageListApi;
import com.fetters.picture.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author : Fetters
 * @description : 图片搜索接口（门面模式）
 * @createDate : 2025/7/19 22:45
 */
@Slf4j
public class ImageSearchApiFacade {

    /**
     * 搜索图片
     * @param imageUrl
     * @return
     */
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.getImagePageUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        return GetImageListApi.getImageList(imageFirstUrl);
    }

    public static void main(String[] args) {
        List<ImageSearchResult> imageList = searchImage("https://www.codefather.cn/logo.png");
        System.out.println("结果列表" + imageList);
    }
}



