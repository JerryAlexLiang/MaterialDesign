package com.liangyang.materialdesign;

/**
 * 创建日期：2017/2/17 on 17:42
 * 作者:杨亮 liangyang
 * 描述:
 */
public class DataInfo {

    private String name;
    private int imageId;
    private String content;

    public DataInfo(String name, int imageId, String content) {
        this.name = name;
        this.imageId = imageId;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getContent() {
        return content;
    }
}
