package com.example.fugibeast.tabdemo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by FugiBeast on 7/28/2017.
 */

public class Tab1 extends Fragment {
    TextView textview;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        view = lf.inflate(R.layout.fragment_1,container,false);

        textview = (TextView) view.findViewById(R.id.list_fragment);
        textview.setText("LIST OF WORKOUTS WILL GO HERE \n KINDA LIKE THE TODOLIST");

        return view;
    }
}
