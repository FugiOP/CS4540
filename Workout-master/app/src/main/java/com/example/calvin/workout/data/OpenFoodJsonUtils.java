package com.example.calvin.workout.data;

import com.example.calvin.workout.models.FoodItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by leona on 8/8/2017.
 */

public class OpenFoodJsonUtils {

    public static ArrayList<FoodItem> getSimpleFoodStringsFromJson(String json) throws JSONException {
        ArrayList<FoodItem> output = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray foodItems = main.getJSONArray("hits");

        for (int i = 0; i < foodItems.length(); i++) {
            JSONObject object = foodItems.getJSONObject(i);
            JSONObject fields = object.getJSONObject("fields");

            String item_name = fields.getString("item_name");
            String brand_name =  fields.getString("brand_name");
            String nf_calories = fields.getString("nf_calories");

            FoodItem item = new FoodItem(item_name,brand_name,nf_calories);

            output.add(item);
        }
        return output;

    }

}