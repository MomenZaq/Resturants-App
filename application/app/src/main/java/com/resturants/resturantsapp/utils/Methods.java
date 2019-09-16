package com.resturants.resturantsapp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

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

}
