package com.resturants.resturantsapp.model;

public class ItemModel {
    String itemName;
    int itemDistance;
    String itemArea;

    public ItemModel(String itemName, int itemDistance, String itemArea) {
        this.itemName = itemName;
        this.itemDistance = itemDistance;
        this.itemArea = itemArea;
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
}
