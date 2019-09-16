package com.resturants.resturantsapp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;

import androidx.core.view.ViewCompat;

import java.util.Locale;

public class LanguageHelper {

    public static void setDirection(View v) {

        int direction = View.LAYOUT_DIRECTION_LTR;

        if (isRTL()) {
            direction = View.LAYOUT_DIRECTION_RTL;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            v.setLayoutDirection(direction);
        } else {
            ViewCompat.setLayoutDirection(v, direction);
        }


    }

    public static Context setLanguageForOreo(Context newBase) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String language = "en";
            if (isRTL()) {
                language = "ar";
            }
            Configuration config = newBase.getResources().getConfiguration();
            //Update your config with the Locale i. e. saved in SharedPreferences
            Locale arabicLocale = new Locale(language);
            Locale.setDefault(arabicLocale);
            config.setLocale(new Locale(language));
            newBase = newBase.createConfigurationContext(config);
            return newBase;
        }
        return null;

    }

    public static boolean isRTL() {
        return true;
    }

}
