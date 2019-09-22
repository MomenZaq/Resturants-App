package com.resturants.resturantsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.resturants.resturantsapp.R;

public class SettingActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void logout(View view) {
    }

    public void back(View view) {
        onBackPressed();
    }
}
