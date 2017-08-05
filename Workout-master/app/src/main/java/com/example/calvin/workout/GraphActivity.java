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
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d");

        // generate Dates
        Calendar calendar = Calendar.getInstance();
        String d1 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String d2 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String d3 = sdf.format(calendar.getTime());


        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 150),
                new DataPoint(2, 210),
                new DataPoint(3, 175)
        });

        // styling series
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        graphView = (GraphView) findViewById(R.id.graphView);
        graphView.addSeries(series);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {d1,d2,d3});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setScrollable(true);
    }
}