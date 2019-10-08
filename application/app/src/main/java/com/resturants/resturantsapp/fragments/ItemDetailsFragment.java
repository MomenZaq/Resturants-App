package com.resturants.resturantsapp.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.model.ItemModel;

public class ItemDetailsFragment extends Fragment implements OnMapReadyCallback {
    View v;
    boolean newCreation = true;
    Activity activity;
    ItemModel itemModel;
    private TextView txvDetails;
    private TextView txvHours;
    private TextView txvPhone;
    private GoogleMap mMap;

    public ItemDetailsFragment() {
    }


    @SuppressLint("ValidFragment")
    public ItemDetailsFragment(Activity activity, ItemModel itemModel) {
        this.activity = activity;
        this.itemModel = itemModel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            newCreation = true;
            v = inflater.inflate(R.layout.fragment_item_details, container, false);


        } else {
            newCreation = false;

        }

        return v;
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (activity == null) {
            return;
        }
        if (newCreation) {


            txvDetails = (TextView) v.findViewById(R.id.txv_details);
            txvHours = (TextView) v.findViewById(R.id.txv_hours);
            txvPhone = (TextView) v.findViewById(R.id.txv_phone);
//            map =  v.findViewById(R.id.map);

            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


            txvDetails.setText(itemModel.getItemArea());
            String openNow="لا";
            if (itemModel.getOpening_hours().equals("true")){
                openNow="نعم";
            }
            txvHours.setText(openNow);

            txvPhone.setText(itemModel.getPhone());
            //execute codes

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = itemModel.getLatLng();
        mMap.addMarker(new MarkerOptions().position(sydney).title(itemModel.getItemName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
    }
}
