package com.resturants.resturantsapp.config;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.utils.Methods;
import com.resturants.resturantsapp.utils.SharedPreferensessClass;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;



public class AppMain extends Application {
    public static final String TAG = AppMain.class
            .getSimpleName();




    @Override
    public void onCreate() {
        super.onCreate();

        Methods.setArabicLocale(getApplicationContext());

        try {

// disable Firebase Database Cache
            FirebaseDatabase.getInstance().setPersistenceEnabled(false);
        } catch (Exception ignored) {
        }


        // Change The APP Default Font
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("Changa-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

//
        if (SharedPreferensessClass.getInstance(getApplicationContext()).getFirstTime()) {

            SharedPreferensessClass.getInstance(getApplicationContext()).setFirstTimeFalse();


        }



    }



}


