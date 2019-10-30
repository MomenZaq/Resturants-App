package com.resturants.resturantsapp.utils;

import android.content.Context;

import com.resturants.resturantsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Place_JSON {


    public static String PLACE_ID_KEY="place_id";
    public static String PLACE_NAME_KEY="place_name";
    public static String VICINITY_KEY="vicinity";
    public static String LAT_KEY="lat";
    public static String LNG_KEY="lng";
    public static String RATE_KEY="rate";
    public static String IMAGE_KEY="image";
    public static String OPENING_KEY="opening";



    /**
     * Receives a JSONObject and returns a list
     */
    public List<HashMap<String, String>> parse(JSONObject jObject, Context context) {

        JSONArray jPlaces = null;
        try {
            /** Retrieves all the elements in the 'places' array */
            jPlaces = jObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getPlaces with the array of json object
         * where each json object represent a place
         */
        return getPlaces(jPlaces,context);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jPlaces, Context context) {
        int placesCount = jPlaces.length();
        List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> place = null;

        /** Taking each place, parses and adds to list object */
        for (int i = 0; i < placesCount; i++) {
            try {
                /** Call getPlace with place JSON object to parse the place */
                place = getPlace((JSONObject) jPlaces.get(i),context);
                placesList.add(place);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    /**
     * Parsing the Place JSON object
     */
    private HashMap<String, String> getPlace(JSONObject jPlace, Context context) {

        System.out.println("THEJSONDATA: " + jPlace);
        HashMap<String, String> place = new HashMap<String, String>();
        String placeId = "-NA-";
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";
        int rating = 0;
        boolean openNow = false;
        String imgUrl = null;
        try {
            // Extracting Place name, if available
            if (!jPlace.isNull("id")) {
                placeId = jPlace.getString("place_id");
            }
        // Extracting Place name, if available
            if (!jPlace.isNull("name")) {
                placeName = jPlace.getString("name");
            }

            // Extracting Place Vicinity, if available
            if (!jPlace.isNull("vicinity")) {
                vicinity = jPlace.getString("vicinity");
            }

            // Extracting Place Vicinity, if available
            if (!jPlace.isNull("rating")) {
                rating = jPlace.getInt("rating");
            }// Extracting Place Vicinity, if available
            if (!jPlace.isNull("opening_hours")) {
                try {
                    openNow = jPlace.getJSONObject("opening_hours").getBoolean("open_now");

                } catch (Exception e) {
                }
            }
            if (!jPlace.isNull("photos")) {
                try {
                    JSONObject imageJson = (JSONObject) jPlace.getJSONArray("photos").get(0);
                    imgUrl = imageJson.getString("photo_reference");
                    imgUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + imgUrl + "&key=" + context.getResources().getString(R.string.api_key);
                } catch (Exception e) {
                }
            }

            latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");




            place.put(PLACE_ID_KEY, placeId);
            place.put(PLACE_NAME_KEY, placeName);
            place.put(VICINITY_KEY, vicinity);
            place.put(LAT_KEY, latitude);
            place.put(LNG_KEY, longitude);
            place.put(RATE_KEY, rating+"");
            place.put(IMAGE_KEY, imgUrl);
            place.put(OPENING_KEY, openNow+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return place;
    }
}