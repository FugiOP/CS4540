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
 * Created by FugiBeast on 8/6/2017.
 */

public class UpdateRoutineFragment extends DialogFragment{
    private Button add;
    private long id;

    //Added status, category and spinner
    private Spinner exercises;
    String exercise;
    private Spinner times;
    String time;

    public UpdateRoutineFragment(){
    }

    //Added status and category to parameters
    public static UpdateRoutineFragment newInstance(String name, long id,String time){
        UpdateRoutineFragment f = new UpdateRoutineFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putString("name", name);
        args.putString("time",time);
        f.setArguments(args);

        return f;
    }

    //To have a way for the activity to get the data from the dialog
    public interface OnUpdateDialogCloseListener {
        //added status and category to parameters
        void closeUpdateDialog(String name, long id,String time);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_adder, container, false);
        add = (Button) view.findViewById(R.id.add);

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
        timeList.add("0");
        timeList.add("30");
        timeList.add("60");
        timeList.add("90");
        timeList.add("120");

        //Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, exerciseList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exercises.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, timeList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        times.setAdapter(dataAdapter2);

        id = getArguments().getLong("id");
        String name1 = getArguments().getString("name");
        String time1 = getArguments().getString("time");
        if (!name1.equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(name1);
            exercises.setSelection(spinnerPosition);
        }
        if (!time1.equals(null)) {
            int spinnerPosition = dataAdapter2.getPosition(time1);
            times.setSelection(spinnerPosition);
        }

        add.setText("Update");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise = exercises.getSelectedItem().toString();
                time = times.getSelectedItem().toString();
                UpdateRoutineFragment.OnUpdateDialogCloseListener activity = (UpdateRoutineFragment.OnUpdateDialogCloseListener) getActivity();
                activity.closeUpdateDialog(exercise,id,time);
                UpdateRoutineFragment.this.dismiss();
            }
        });

        return view;
    }
}
