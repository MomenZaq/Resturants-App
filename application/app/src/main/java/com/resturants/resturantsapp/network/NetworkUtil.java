package com.resturants.resturantsapp.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.resturants.resturantsapp.utils.Place_JSON;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class NetworkUtil {


    public static class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;
        private Context context;
        boolean parseJson;
        onFinishedConnection onFinishedConnection;

        public PlacesTask(Context baseContext, boolean parseJson, onFinishedConnection onFinishedConnection) {
            this.context = baseContext;
            this.parseJson = parseJson;
            this.onFinishedConnection = onFinishedConnection;
        }

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            if (parseJson){

            ParserTask parserTask = new ParserTask(context, onFinishedConnection);
            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParserTask
            parserTask.execute(result);
            }else {
                onFinishedConnection.onFinished(result);
            }
        }
    }

    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);

            }

            data = sb.toString();

            System.out.println("THERESPONSE: " + data);
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public static class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;
        private Context context;
        onFinishedConnection onFinishedConnection;

        public ParserTask(Context context, onFinishedConnection onFinishedConnection) {
            this.context = context;
            this.onFinishedConnection = onFinishedConnection;
        }

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            Place_JSON placeJson = new Place_JSON();

            try {
                jObject = new JSONObject(jsonData[0]);

                places = placeJson.parse(jObject, context);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            onFinishedConnection.onFinished(list);
        }
    }


    public interface onFinishedConnection {
        void onFinished(List<HashMap<String, String>> list);

        void onFinished(String response);
    }

}
