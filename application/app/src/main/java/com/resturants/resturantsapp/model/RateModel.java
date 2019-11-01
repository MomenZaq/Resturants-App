package com.resturants.resturantsapp.model;

public class RateModel {
    String userName;
    String userEmail;
    String itemName;
    String comment;

    public RateModel() {
    }

    public RateModel(String comment) {
        this.comment = comment;
    }

    public RateModel(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
    }

    public RateModel(String userName, String userEmail, String itemName, String comment) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.itemName = itemName;
        this.comment = comment;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
