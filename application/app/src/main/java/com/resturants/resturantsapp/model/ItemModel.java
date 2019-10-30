package com.resturants.resturantsapp.model;

import com.google.android.gms.maps.model.LatLng;

public class ItemModel {
    String itemId;
    String itemName;
    int itemDistance;
    String itemArea;
    int rating;
    String opening_hours;
    String phone;
    String imgUrl;
    LatLng latLng;


    public ItemModel(String itemId,String itemName, int itemDistance, String itemArea, int rating, String opening_hours, String phone, String imgUrl, LatLng latLng) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDistance = itemDistance;
        this.itemArea = itemArea;
        this.rating = rating;
        this.opening_hours = opening_hours;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.latLng = latLng;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemDistance() {
        return itemDistance;
    }

    public void setItemDistance(int itemDistance) {
        this.itemDistance = itemDistance;
    }

    public String getItemArea() {
        return itemArea;
    }

    public void setItemArea(String itemArea) {
        this.itemArea = itemArea;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
