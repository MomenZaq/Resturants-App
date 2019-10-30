package com.resturants.resturantsapp.activities;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.adapters.MainItemAdapter;
import com.resturants.resturantsapp.model.ItemModel;
import com.resturants.resturantsapp.network.NetworkUtil;
import com.resturants.resturantsapp.utils.MyLocationListener;
import com.resturants.resturantsapp.utils.Place_JSON;
import com.resturants.resturantsapp.utils.SharedPreferensessClass;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ItemsActivity extends ParentActivity {
    private ConstraintLayout constraintLayout;
    private ImageView imgSetting;
    private ImageView imgLogout;
    private ImageView imgBack;
    private AppCompatEditText edtSearch;
    private TextView txvNear;
    private RecyclerView recycler;
    private ProgressBar progress;
    private TextView txvNoData;

    int distance;
    String selectedItem;
    public static String ITEM_SELECTED_INTENT = "selected_type";

    public static String ITEM_TYPE_RESTAURANT = "restaurant";
    public static String ITEM_TYPE_COFFEE = "cafe";
    public static String ITEM_TYPE_RESTAURANT_COFFEE = "restaurant&cafe";

    String mLatitude = "";
    String mLongitude = "";
    List<ItemModel> itemModelList = new ArrayList<>();
    MainItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);


        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        imgSetting = (ImageView) findViewById(R.id.img_setting);
        imgLogout = (ImageView) findViewById(R.id.img_logout);
        imgBack = (ImageView) findViewById(R.id.img_back);
        edtSearch = (AppCompatEditText) findViewById(R.id.edt_search);
        txvNear = (TextView) findViewById(R.id.txv_near);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        progress = (ProgressBar) findViewById(R.id.progress);
        txvNoData = (TextView) findViewById(R.id.txv_no_data);

        progress.setVisibility(View.VISIBLE);
        distance = SharedPreferensessClass.getInstance(getBaseContext()).getSearchDistance();

        selectedItem = getIntent().getStringExtra(ITEM_SELECTED_INTENT);
//set vertical layout manager for recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);


        //just for test
//        List<ItemModel> itemModelList = new ArrayList<>();
//        ItemModel itemModel = new ItemModel("المكان 1", 200, "المنطقة 1");
//        itemModelList.add(itemModel);
//        itemModel = new ItemModel("المكان 2", 300, "المنطقة 2");
//        itemModelList.add(itemModel);
//        itemModel = new ItemModel("المكان 3", 400, "المنطقة 3");
//        itemModelList.add(itemModel);
//        itemModel = new ItemModel("المكان 4", 200, "المنطقة 4");
//        itemModelList.add(itemModel);
//        itemModel = new ItemModel("المكان 5", 100, "المنطقة 5");
//        itemModelList.add(itemModel);
//        itemModel = new ItemModel("المكان 6", 400, "المنطقة 6");
//        itemModelList.add(itemModel);
//        itemModel = new ItemModel("المكان 7", 200, "المنطقة 7");
//        itemModelList.add(itemModel);

//        MainItemAdapter itemAdapter = new MainItemAdapter(itemModelList, this);
//        recycler.setAdapter(itemAdapter);

        if (selectedItem.equals(ITEM_TYPE_RESTAURANT_COFFEE)) {
            getData(ITEM_TYPE_RESTAURANT);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData(ITEM_TYPE_COFFEE);

                }
            }, 3000);
            txvNear.setText(getResources().getString(R.string.near_restaurant_coffee));
        } else if (selectedItem.equals(ITEM_TYPE_RESTAURANT)) {

            getData(selectedItem);
            txvNear.setText(getResources().getString(R.string.near_restaurant));

        } else if (selectedItem.equals(ITEM_TYPE_COFFEE)) {
            getData(selectedItem);
            txvNear.setText(getResources().getString(R.string.near_cofee));
        }


        //here we set listener for search
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(edtSearch.getText());
                if (itemModelList == null || itemModelList.isEmpty()) {
                    return;
                }
                if (edtSearch.getText().length() == 0) {
                    setAdapter(itemModelList);

                } else {
                    ArrayList<ItemModel> list = new ArrayList<>();

                    for (ItemModel object : itemModelList) {
                        if (object.getItemName().contains(edtSearch.getText())) {
                            list.add(object);
                        }
                    }
                    setAdapter(list);
                }
            }
        });


    }

    private void getData(String type) {
        System.out.println("GETDATANOW");

        LatLng latLng = getLocation();

        if (latLng == null) {
            //the location null; try again after 5 sec
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData(type);
                }
            }, 5000);


        } else {
            mLatitude = latLng.latitude + "";
            mLongitude = latLng.longitude + "";
            StringBuilder sbValue = new StringBuilder(getAPILike(mLatitude, mLongitude, type));
            NetworkUtil.PlacesTask placesTask = new NetworkUtil.PlacesTask(getBaseContext(),true, new NetworkUtil.onFinishedConnection() {
                @Override
                public void onFinished(List<HashMap<String, String>> list) {

                    if (!list.isEmpty()) {
                        Log.d("Map", "list size: " + list.size());
                        for (int i = 0; i < list.size(); i++) {


                            HashMap<String, String> hmPlace = list.get(i);

                            // Getting latitude of the place
                            double lat = Double.parseDouble(hmPlace.get(Place_JSON.LAT_KEY));
                            // Getting longitude of the place
                            double lng = Double.parseDouble(hmPlace.get(Place_JSON.LNG_KEY));
                            // Getting id
                            String id = hmPlace.get(Place_JSON.PLACE_ID_KEY);
                            // Getting name
                            String name = hmPlace.get(Place_JSON.PLACE_NAME_KEY);
                            // Getting vicinity
                            String vicinity = hmPlace.get(Place_JSON.VICINITY_KEY);
                            // Getting Rate
                            String rate = hmPlace.get(Place_JSON.RATE_KEY);
                            // Getting Image
                            String imageUrl = hmPlace.get(Place_JSON.IMAGE_KEY);
                            // Getting OpenNow?
                            String openNow = hmPlace.get(Place_JSON.OPENING_KEY);

                            Log.d("Map", "place: " + name);


                            Location locationA = new Location("point A");
                            locationA.setLatitude(Double.parseDouble(mLatitude));
                            locationA.setLongitude(Double.parseDouble(mLongitude));
                            Location locationB = new Location("point B");
                            locationB.setLatitude(lat);
                            locationB.setLongitude(lng);
                            int distance = (int) locationA.distanceTo(locationB);


                            ItemModel itemModel = new ItemModel(id,name, distance, vicinity, Integer.parseInt(rate), openNow, "-", imageUrl, new LatLng(lat, lng));
                            itemModelList.add(itemModel);

                        }
                    }

                    setAdapter(itemModelList);
                }

                @Override
                public void onFinished(String response) {

                }
            });
            placesTask.execute(sbValue.toString());
        }
    }

    public LatLng getLocation() {
        Double latitude = 0.0, longitude;
        LocationManager mlocManager = null;
        MyLocationListener mlocListener;
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            latitude = MyLocationListener.latitude;
            longitude = MyLocationListener.longitude;
            mlocListener.stopListener();
            return new LatLng(latitude, longitude);


        } else {
            Toast.makeText(getApplicationContext(), "GPS is currently off...", Toast.LENGTH_LONG).show();
            //open GPS
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        return null;
    }

    public StringBuilder getAPILike(String mLatitude, String mLongitude, String type) {

        //use your current location here

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=" + distance);
        sb.append("&types=" + type);
        sb.append("&sensor=true");
        sb.append("&key=" + getResources().getString(R.string.api_key));

        Log.d("Map", "api: " + sb.toString());

        return sb;
    }


    private void setAdapter(List<ItemModel> itemModelList) {

        progress.setVisibility(View.GONE);
        //sort the items by distance.
        if (itemModelList.isEmpty()) {
            txvNoData.setVisibility(View.VISIBLE);
        } else {
            txvNoData.setVisibility(View.GONE);
            Collections.sort(itemModelList, new Comparator<ItemModel>() {
                @Override
                public int compare(ItemModel o1, ItemModel o2) {
                    return o1.getItemDistance() - o2.getItemDistance();
                }
            });
        }
        if (itemAdapter == null) {
//                  create new instance
            itemAdapter = new MainItemAdapter(itemModelList, this);
            recycler.setAdapter(itemAdapter);
        } else {
//                  just update
            itemAdapter.setList(itemModelList);
            itemAdapter.notifyDataSetChanged();
        }


    }


    public void setting(View view) {
        openSetting();
    }

    public void logout(View view) {
        logout();
    }

    public void back(View view) {
        onBackPressed();
    }
}
