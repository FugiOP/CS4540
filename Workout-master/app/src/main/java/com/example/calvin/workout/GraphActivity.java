package com.example.calvin.workout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.calvin.workout.data.Contract;
import com.example.calvin.workout.data.DBHelper;
import com.example.calvin.workout.data.UserContract;
import com.example.calvin.workout.data.userDBHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * Created by FugiBeast on 7/28/2017.
 */

public class GraphActivity extends AppCompatActivity {
    GraphView graphView;
    Cursor weight_cursor;
    Cursor date_calorie_cursor;
    private SQLiteDatabase db_weight;
    private SQLiteDatabase db_date_calorie;
    private userDBHelper helper_weight;
    private DBHelper helper_date_calorie;
    String[] dateArray;
    DataPoint[] dataArray;

    int user_weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        helper_weight = new userDBHelper(this);
        db_weight = helper_weight.getReadableDatabase();
        weight_cursor = getUserWeight(db_weight);
        weight_cursor.moveToFirst();
        user_weight = weight_cursor.getInt(weight_cursor.getColumnIndex(UserContract.TABLE_USER.COLUMN_NAME_WEIGHT));

        helper_date_calorie = new DBHelper(this);
        db_date_calorie = helper_date_calorie.getReadableDatabase();
        date_calorie_cursor = getUserData(db_date_calorie);

        dataArray = new DataPoint[date_calorie_cursor.getCount()];
        dateArray = new String[date_calorie_cursor.getCount()];

        Log.d("CURSOR",""+date_calorie_cursor.getCount());
        getCaloriesDates(date_calorie_cursor);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataArray);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY MMM d");

        // generate Dates
        Calendar calendar = Calendar.getInstance();
        String d1 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        String d2 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        String d3 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        String d4 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        String d5 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        String d6 = sdf.format(calendar.getTime());




        // styling series
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        graphView = (GraphView) findViewById(R.id.graphView);
        graphView.addSeries(series);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(dateArray);
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setScrollable(true);
    }
    private Cursor getUserWeight(SQLiteDatabase db){
        return db.query(
                UserContract.TABLE_USER.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Cursor getUserData(SQLiteDatabase db){
        return db.query(
                Contract.TABLE_USER_CALORIES.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private void getCaloriesDates(Cursor cursor){
        while(cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex(Contract.TABLE_USER_CALORIES.COLUMN_NAME_DATE));
            double calories = cursor.getDouble(cursor.getColumnIndex(Contract.TABLE_USER_CALORIES.COLUMN_NAME_CALORIES));
            int index = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_USER_CALORIES._ID));
            dateArray[index-1] = date;
            dataArray[index-1] = new DataPoint(index,calories);
        }
    }
}