package com.example.calvin.workout;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calvin.workout.data.Contract;
import com.example.calvin.workout.data.DBHelper;
import com.example.calvin.workout.models.TimerModel;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by FugiBeast on 8/5/2017.
 */

public class RoutineActivity extends AppCompatActivity implements View.OnClickListener,TextToSpeech.OnInitListener,AddRoutineFragment.OnDialogCloseListener, UpdateRoutineFragment.OnUpdateDialogCloseListener, AdapterView.OnItemSelectedListener{
    Button btn;
    Button timerStart;
    static ArrayList<TimerModel> timerlist;
    TextView timerView;
    TextToSpeech textToSpeech;
    private int MY_DATA_CHECK_CODE = 0;
    int count = 0;
    private boolean timerHasStarted = false;
    CountDownTimer countDownTimer1;
    CountDownTimer countDownTimer2;

    private RecyclerView rv;
    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    RoutineListAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        timerView = (TextView)findViewById(R.id.timerView);

        btn = (Button) findViewById(R.id.addExercise);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                AddRoutineFragment frag = new AddRoutineFragment();
                frag.show(fm,"addroutinefragment");
            }
        });

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        timerlist = new ArrayList<>();

        timerStart = (Button)findViewById(R.id.timerStart);
        timerStart.setOnClickListener(this);

        rv = (RecyclerView) findViewById(R.id.routineList);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (db != null) db.close();
        if (cursor != null) cursor.close();
        if (textToSpeech != null) textToSpeech.shutdown();
    }
    @Override
    protected void onStart() {
        super.onStart();

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        cursor = getAllItems(db);

        adapter = new RoutineListAdapter(cursor, new RoutineListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos ,String name, long id, String time) {
                FragmentManager fm = getFragmentManager();

                UpdateRoutineFragment frag = UpdateRoutineFragment.newInstance(name,id,time);
                frag.show(fm, "updatetodofragment");
            }
        });
        rv.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                removeExercise(db, id);
                adapter.swapCursor(getAllItems(db));
            }
        }).attachToRecyclerView(rv);
    }
    @Override
    public void closeDialog(String name,String time) {
        addExercise(db,name,time);
        cursor = getAllItems(db);
        adapter.swapCursor(cursor);
    }
    private Cursor getAllItems(SQLiteDatabase db) {
        Cursor result = db.query(
                Contract.TABLE_USER_WORKOUT.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while(result.moveToNext()){
            TimerModel entry = new TimerModel(result.getString(result.getColumnIndex(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_TIME)),result.getString(result.getColumnIndex(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_EXERCISE)),result.getLong(result.getColumnIndex(Contract.TABLE_USER_WORKOUT._ID)));
            timerlist.add(entry);
        }
        return result;
    }
    private long addExercise(SQLiteDatabase db, String name,String time) {
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_EXERCISE, name);
        cv.put(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_TIME, time);

        return db.insert(Contract.TABLE_USER_WORKOUT.TABLE_NAME, null, cv);
    }
    private boolean removeExercise(SQLiteDatabase db, long id) {

        return db.delete(Contract.TABLE_USER_WORKOUT.TABLE_NAME, Contract.TABLE_USER_WORKOUT._ID + "=" + id, null) > 0;
    }
    private int updateExercise(SQLiteDatabase db, String name, long id,String time){
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_EXERCISE, name);
        cv.put(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_TIME, time);

        return db.update(Contract.TABLE_USER_WORKOUT.TABLE_NAME, cv, Contract.TABLE_USER_WORKOUT._ID + "=" + id, null);
    }
    @Override
    public void closeUpdateDialog(String name, long id,String time) {
        updateExercise(db,name, id,time);
        adapter.swapCursor(getAllItems(db));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Checking what item in spinner was chosen
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        cursor = getAllItems(db);
        adapter.swapCursor(cursor);
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void startFirstTimer(){
        if(count<timerlist.size()){
            final String name = timerlist.get(count).getName();
            final String time = timerlist.get(count).getTime();
            countDownTimer1 = new CountDownTimer(8000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(millisUntilFinished<=8000 && millisUntilFinished>7000){
                        speak("Start "+name+" in 5 seconds");
                    }
                    if(millisUntilFinished<6000){
                        speak(""+millisUntilFinished/1000);
                        timerView.setText(millisUntilFinished/1000+"'s");
                    }
                }

                @Override
                public void onFinish() {
                    startSecondTimer();
                }
            }.start();
        }
        if(count==timerlist.size()){
            timerView.setText("Workout Routine Completed");
            speak("Workout Routine Completed");
        }
    }
    public void startSecondTimer(){
        if(count<timerlist.size()){
            final String name = timerlist.get(count).getName();
            final String time = timerlist.get(count).getTime();
            countDownTimer2 = new CountDownTimer(Integer.valueOf(time)*1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerView.setText(millisUntilFinished/1000+"'s");
                    if(millisUntilFinished<=6000 && millisUntilFinished>=5000){
                        speak("5 seconds left");
                    }
                    if(millisUntilFinished<=5000){
                        speak(millisUntilFinished/1000+"");
                    }
                }

                @Override
                public void onFinish() {
                    timerView.setText(0+"'s");
                    count++;
                    startFirstTimer();
                }
            }.start();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                textToSpeech = new TextToSpeech(this, this);
            }
            else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.US);
        }else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
    public void speak(String text){
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD,null);
    }

    @Override
    public void onClick(View v)
    {
        if (!timerHasStarted)
        {
            startFirstTimer();
            timerHasStarted = true;
            timerStart.setText("Stop");
        }
        else
        {
            if(countDownTimer1!=null){
                countDownTimer1.cancel();
            }
            if(countDownTimer2!=null) {
                countDownTimer2.cancel();
            }
            timerHasStarted = false;
            timerStart.setText("Reset");
        }
    }
}
