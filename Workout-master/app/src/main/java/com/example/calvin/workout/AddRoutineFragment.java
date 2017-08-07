package com.example.calvin.workout;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FugiBeast on 8/5/2017.
 */

public class AddRoutineFragment extends DialogFragment{
    Spinner exercises;
    Spinner times;
    Button add;
    String exercise;
    String time;

    public AddRoutineFragment() {
    }

    public interface OnDialogCloseListener {
        void closeDialog(String name,String time);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_adder,container,false);

        //reference to spinner in fragment
        exercises = (Spinner) view.findViewById(R.id.exercises);
        times = (Spinner) view.findViewById(R.id.timeCount);

        //options for spinner
        List<String> exerciseList = new ArrayList<>();
        exerciseList.add("Push Ups");
        exerciseList.add("Sit Ups");
        exerciseList.add("Leg Raises");
        exerciseList.add("High Knees");
        exerciseList.add("Squats");
        exerciseList.add("Bulgarian Squats");
        exerciseList.add("Jumping Squats");
        exerciseList.add("Jumping Burpees");
        exerciseList.add("Jump Rope");

        List<String> timeList = new ArrayList<>();
        timeList.add("30");
        timeList.add("60");
        timeList.add("90");
        timeList.add("120");

        //Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, exerciseList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exercises.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, timeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        times.setAdapter(dataAdapter);

        add = (Button) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogCloseListener activity = (OnDialogCloseListener) getActivity();
                exercise = exercises.getSelectedItem().toString();
                time = times.getSelectedItem().toString();
                activity.closeDialog(exercise,time);
                AddRoutineFragment.this.dismiss();
            }
        });

        return view;
    }
}
