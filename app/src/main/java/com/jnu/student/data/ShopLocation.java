package com.jnu.student.data;

// ShopLocation类，用于表示商店位置信息
public class ShopLocation {
    // 商店名称
    private String name;
    // 商店经度
    private double longitude;
    // 商店备注信息
    private String memo;
    // 商店纬度
    private double latitude;

    // 设置商店经度
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // 获取商店经度
    public double getLongitude() {
        return longitude;
    }

    // 设置商店备注信息
    public void setMemo(String memo) {
        this.memo = memo;
    }

    // 获取商店备注信息
    public String getMemo() {
        return memo;
    }

    // 设置商店名称
    public void setName(String name) {
        this.name = name;
    }

    // 获取商店名称
    public String getName() {
        return name;
    }

    // 设置商店纬度
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // 获取商店纬度
    public double getLatitude() {
        return latitude;
    }
}
