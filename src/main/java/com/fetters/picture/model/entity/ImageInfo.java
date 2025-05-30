package com.fetters.picture.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageInfo {
    private int width;
    private int height;
    private String format;
}