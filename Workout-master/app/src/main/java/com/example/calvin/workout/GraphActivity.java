package com.example.calvin.workout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Created by FugiBeast on 7/28/2017.
 */

public class GraphActivity extends AppCompatActivity {
    GraphView graphView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
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


        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 160),
                new DataPoint(2, 175),
                new DataPoint(3, 201),
                new DataPoint(4, 180),
                new DataPoint(5, 205),
                new DataPoint(6, 200)
        });

        // styling series
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        graphView = (GraphView) findViewById(R.id.graphView);
        graphView.addSeries(series);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {d6,d5,d4,d3,d2,d1});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setScrollable(true);
    }
}