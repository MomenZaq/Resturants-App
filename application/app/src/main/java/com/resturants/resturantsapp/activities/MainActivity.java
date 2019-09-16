package com.resturants.resturantsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.utils.LanguageHelper;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends ParentActivity {

    private ConstraintLayout constraintLayout;
    private ImageView imgSetting;
    private ImageView imgLogout;
    private TextView textView;
    private CardView cardRestaurants;
    private CardView cardCoffee;
    private CardView cardRestaurantsCoffee;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        imgSetting = (ImageView) findViewById(R.id.img_setting);
        imgLogout = (ImageView) findViewById(R.id.img_logout);
        textView = (TextView) findViewById(R.id.textView);
        cardRestaurants = (CardView) findViewById(R.id.card_restaurants);
        cardCoffee = (CardView) findViewById(R.id.card_coffee);
        cardRestaurantsCoffee = (CardView) findViewById(R.id.card_restaurants_coffee);


        cardRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(MainActivity.this,
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
            }
        });

    }
}
