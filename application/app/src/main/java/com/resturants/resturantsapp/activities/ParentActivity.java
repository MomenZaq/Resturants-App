package com.resturants.resturantsapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.utils.LanguageHelper;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public abstract class ParentActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        // Change The APP Default Language To Arabic
        Context c = LanguageHelper.setLanguageForOreo(newBase);
        if (c != null) {
            newBase = c;
        }

        // Change The Activity Default Font
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set The Direction Of The App "RTL"
        LanguageHelper.setDirection(getWindow().getDecorView());
        super.onCreate(savedInstanceState);
    }


    public void openSetting(){
        Intent intent = new Intent(this, SettingActivity.class);
           // create fade animation
                 Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(this,
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
        startActivity(intent, bundle);
    }
}
