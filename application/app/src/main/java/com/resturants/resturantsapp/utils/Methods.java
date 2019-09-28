package com.resturants.resturantsapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {
    public static void setArabicLocale(Context context) {
        try {
            Locale arabicLocale = new Locale("ar");
            Locale.setDefault(arabicLocale);
            Configuration anConfiguration = new Configuration();
            Resources res = context.getResources();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                anConfiguration.setLocale(arabicLocale);
                new Configuration(res.getConfiguration()).setLocale(arabicLocale);
            } else {
                anConfiguration.locale = arabicLocale;

            }
            res.updateConfiguration(anConfiguration, context.getResources().getDisplayMetrics());
        } catch (Exception ignored) {
        }
    }
    public static int getScreenWidth(Activity activity) {
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//            int newTabMinWidth = parent.getWidth() / list.size();
//        return displayMetrics.widthPixels;


        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        } else {
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }

        return displayMetrics.widthPixels;

    }


    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
