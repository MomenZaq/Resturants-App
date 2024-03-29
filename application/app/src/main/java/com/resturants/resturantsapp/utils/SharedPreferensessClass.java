package com.resturants.resturantsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferensessClass {

    private static SharedPreferensessClass Instance;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private Context context;

    public SharedPreferensessClass(Context context) {
        this.context = context;
    }


    public static SharedPreferensessClass getInstance(Context context) {

        if (Instance == null) {
            Instance = new SharedPreferensessClass(context);
        }
        init(context);
        return Instance;
    }


    public static void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("SharedFileName", Context.MODE_PRIVATE);
        }

        if (editor == null) {
            editor = sharedPreferences.edit();
        }
    }

    public void setFirstTimeFalse() {

        editor.putBoolean("firstTime", false);
        editor.commit();
    }

    public boolean getFirstTime() {
        return sharedPreferences.getBoolean("firstTime", true);
    }


    public String getUserName() {

        return sharedPreferences.getString("UserName", "");
    }

    public void setUserName(String userName) {

        editor.putString("UserName", userName);
        editor.commit();
    }

    public String getUserEmail() {

        return sharedPreferences.getString("UserEmail", "");
    }

    public void setUserEmail(String userEmail) {

        editor.putString("UserEmail", userEmail);
        editor.commit();
    }

    public String getUserPass() {

        return sharedPreferences.getString("UserPass", "");
    }

    public void setUserPass(String userEmail) {

        editor.putString("UserPass", userEmail);
        editor.commit();
    }


    public int getUserId() {

        return sharedPreferences.getInt("UserId", 0);
    }

    public void setUserId(int userId) {

        editor.putInt("UserId", userId);
        editor.commit();
    }


    public int getSearchDistance() {

        return sharedPreferences.getInt("SearchDistance", 10000);
    }

    public void setSearchDistance(int searchDistance) {

        editor.putInt("SearchDistance", searchDistance);
        editor.commit();
    }

    public void signOut() {
        setUserId(0);
        setUserName("");
        setUserEmail("");


    }


    public boolean getRateItemName(String itemName) {
// if returned true: user already has a comment.
// if returned false: user doesn't have a comment; he can comment
        return sharedPreferences.getBoolean(itemName, false);
    }

    public void setRateItemName(String itemName) {

        editor.putBoolean(itemName, true);
        editor.commit();
    }

    public boolean getBtnRateItemName(String itemName) {
// if returned true: user already has a comment.
// if returned false: user doesn't have a comment; he can comment
        return sharedPreferences.getBoolean("btn_"+itemName, false);
    }

    public void setBtnRateItemName(String itemName) {

        editor.putBoolean("btn_"+itemName, true);
        editor.commit();
    }
}
