package com.example.calvin.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView)findViewById(R.id.profile);
        tv.setText("Add Profile settings here");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.routine){
            Intent intent = new Intent(this, RoutineActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.map){
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.music) {
            Intent intent = new Intent(this, MusicActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.graph) {
            Intent intent = new Intent(this, GraphActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}