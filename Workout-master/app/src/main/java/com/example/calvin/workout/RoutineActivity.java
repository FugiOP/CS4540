package com.example.calvin.workout;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

/**
 * Created by FugiBeast on 8/5/2017.
 */

public class RoutineActivity extends AppCompatActivity{
    Button fab;
    RecyclerView rv;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        fab = (Button) findViewById(R.id.addExercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                AddRoutineFragment frag = new AddRoutineFragment();
                frag.show(fm,"addroutinefragment");
            }
        });

        rv = (RecyclerView) findViewById(R.id.routineList);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
