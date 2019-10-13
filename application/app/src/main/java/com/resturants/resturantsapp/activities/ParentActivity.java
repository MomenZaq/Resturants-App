package com.resturants.resturantsapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.utils.LanguageHelper;
import com.resturants.resturantsapp.utils.SharedPreferensessClass;

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


    public void openSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        // create fade animation
        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(this,
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
        startActivity(intent, bundle);
    }

    public void logout() {
        if (!SharedPreferensessClass.getInstance(getBaseContext()).getUserEmail().equals("")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.log_out));
            builder.setMessage(getResources().getString(R.string.are_you_sure_you_want_to_log_out));
            builder.setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) -> {

                SharedPreferensessClass.getInstance(getBaseContext()).signOut();
                Toast.makeText(this, getResources().getString(R.string.logout_done), Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            });

            builder.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

            builder.show();


        }
    }
}
