package com.example.calvin.workout.data;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by leona on 8/8/2017.
 */

public class Foodutils {
    //6. 2pts: Put this method in your NetworkUtils class:
    public static final String STATIC_URL =
            "https://api.nutritionix.com/v1_1/search/";
    public static final String BASE_URL = STATIC_URL;


    public static final String appKey = "ec9e2990978081871f45fc9fe8d97f3b";
    public static String PARAM_APIKEY = "appKey";

    public static final String appId= "f85a3dc7";
    public static String PARAM_APPID = "appId";

    public static final String results = "0:15";
    public static String PARAM_RESULTS = "results";

    public static final String cal_max = "5000";
    public static String PARAM_CAL_MAX = "cal_max";

    public static final String cal_min = "0";
    public static String PARAM_CAL_MIN= "cal_min";

    public static final String fields = "item_name,brand_name,nf_calories";
    public static String PARAM_FIELDS = "fields";









    public static URL makeUrl(String search) {
        String NEW_URL = BASE_URL.concat("phrase=" + search);
        Uri buildUri = Uri.parse(NEW_URL).buildUpon()
                .appendQueryParameter(PARAM_RESULTS, results)
                .appendQueryParameter(PARAM_CAL_MIN, cal_min)
                .appendQueryParameter(PARAM_CAL_MAX, cal_max)
                .appendQueryParameter(PARAM_FIELDS, fields)
                .appendQueryParameter(PARAM_APPID, appId)
                .appendQueryParameter(PARAM_APIKEY, appKey)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput)
            {
                return scanner.next();
            }
            else {
                return null;
            }

        }
        finally {
            urlConnection.disconnect();
        }
    }
}