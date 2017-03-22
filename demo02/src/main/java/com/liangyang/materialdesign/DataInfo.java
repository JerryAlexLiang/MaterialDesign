package com.liangyang.materialdesign;

/**
 * 创建日期：2017/2/17 on 17:42
 * 作者:杨亮 liangyang
 * 描述:
 */
public class DataInfo {

    private int number;
    private String name;
    private int imageId;

    public DataInfo(int number, String name, int imageId) {
        this.number = number;
        this.name = name;
        this.imageId = imageId;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
