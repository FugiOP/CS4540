package com.example.calvin.workout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.calvin.workout.data.Foodutils;
import com.example.calvin.workout.data.OpenFoodJsonUtils;
import com.example.calvin.workout.models.FoodItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by leona on 8/8/2017.
 */

public class FoodActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<FoodItem>> {

    private RecyclerView rView;
    private  FoodsAdapter mAdapter;
    private ProgressBar progress;
    private EditText search;
    private static final int SEARCH_LOADER = 0;
    public final static String SEARCH_QUERY = "";
    static final String TAG = "foodactivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);
        progress = (ProgressBar) findViewById(R.id.progress);

        search = (EditText) findViewById(R.id.searchQuery);

        rView = (RecyclerView) findViewById(R.id.rv_foods);

        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public Loader<ArrayList<FoodItem>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<FoodItem>>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(args == null) {
                    return;
                }
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public ArrayList<FoodItem> loadInBackground() {
                String query = search.getText().toString();
                ArrayList<FoodItem> output = null;
                URL url = Foodutils.makeUrl(query);

                try {
                    String json = Foodutils.getResponseFromHttpUrl(url);
                    output = OpenFoodJsonUtils.getSimpleFoodStringsFromJson(json);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }

                return output;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FoodItem>> loader, final ArrayList<FoodItem> foodItems) {
        progress.setVisibility(View.GONE);
        if (foodItems != null) {
            FoodsAdapter newAdapter = new FoodsAdapter(foodItems, new FoodsAdapter.ItemClickListener() {
                @Override
                public void onItemClick(int clickedItemIndex) {
                    Context context = FoodActivity.this;
                    String url = foodItems.get(clickedItemIndex).getBrand_name();

                    //Splits up info wfrom string
                    String[] parts = url.split(" ");
                    String part1 = parts[0]; // brand:
                    String part2 = parts[1]; // actual brand like Mcds or gatorade ,etc.

                    // Uri website = Uri.parse(url);
                    String search = "https://www.google.com/search?rlz=1C1CHBF_enUS749US749&q=";
                    String searchBrand = search+part2;
                    Uri website = Uri.parse(searchBrand);


                    Intent intent = new Intent(Intent.ACTION_VIEW, website);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                }






            });

            rView.setAdapter(newAdapter);


        }

    }

    /*
        public void sendCalories(View v) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }
    */
    @Override
    public void onLoaderReset(Loader<ArrayList<FoodItem>> loader) {

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        if (itemNumber == R.id.search) {
            Context context = FoodActivity.this;
            String textToShow = "Search completed";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            //***************** These lines are used to save the state of the application whenever you exit and open the application
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.restartLoader(SEARCH_LOADER, null , this).forceLoad();
        }

        return super.onOptionsItemSelected(item);
    }

}