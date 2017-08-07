package com.example.calvin.workout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calvin.workout.data.Contract;
import com.example.calvin.workout.data.UserContract;
import com.example.calvin.workout.data.userDBHelper;
import com.example.calvin.workout.models.userProfile;

import java.util.ArrayList;
import java.util.List;

import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_FEET;
import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_GENDER;
import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_INCHES;
import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_NAME;
import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_WEIGHT;

/**
 * Created by FugiBeast on 8/6/2017.
 */

public class UpdateProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private SQLiteDatabase db;
    private userDBHelper helper;
    Button submitButton;
    EditText weightText;
    EditText nameText;
    Spinner gSpinner;
    Spinner fSpinner;
    Spinner iSpinner;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_form);
        //***********spinners
        // Spinner element
        gSpinner = (Spinner) findViewById(R.id.gender_spinner);
        fSpinner = (Spinner) findViewById(R.id.ft_spinner);
        iSpinner = (Spinner) findViewById(R.id.inches_spinner);
        nameText = (EditText)findViewById(R.id.name);
        weightText = (EditText)findViewById(R.id.weight);

        // Spinner click listener
        gSpinner.setOnItemSelectedListener(this);
        fSpinner.setOnItemSelectedListener(this);
        iSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        //Gender Options
        List<String> sex = new ArrayList<String>();
        sex.add("Choose your Gender");
        sex.add("M");
        sex.add("F");
        //height by feet options 8
        List<String> feet = new ArrayList<String>();
        feet.add("Choose your height by ft");
        feet.add("1");
        feet.add("2");
        feet.add("3");
        feet.add("4");
        feet.add("5");
        feet.add("6");
        feet.add("7");
        feet.add("8");
        //height by inches options
        final List<String> inChes = new ArrayList<String>();
        inChes.add("Choose your height by inches");
        inChes.add("0");
        inChes.add("1");
        inChes.add("2");
        inChes.add("3");
        inChes.add("4");
        inChes.add("5");
        inChes.add("6");
        inChes.add("7");
        inChes.add("8");
        inChes.add("9");
        inChes.add("10");
        inChes.add("11");



        // Creating adapter for gender spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sex);
        // Creating adapter for foot spinner
        ArrayAdapter<String> dataFAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, feet);
        // Creating adapter for inches spinner
        ArrayAdapter<String> dataIAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, inChes);


        // Drop down layout style - list view with radio button for gender
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Drop down layout style - list view with radio button for foot
        dataFAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Drop down layout style - list view with radio button for inChes
        dataIAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        // attaching data adapter to spinner for gender
        gSpinner.setAdapter(dataAdapter);
        // attaching data adapter to spinner for foot
        fSpinner.setAdapter(dataFAdapter);
        // attaching data adapter to spinner for inches
        iSpinner.setAdapter(dataIAdapter);

        helper = new userDBHelper(this);
        db = helper.getWritableDatabase();
        cursor = getUserData(db);
        cursor.moveToFirst();
        nameText.setText(cursor.getString(cursor.getColumnIndex(UserContract.TABLE_USER.COLUMN_NAME_NAME)));
        weightText.setText(cursor.getString(cursor.getColumnIndex(UserContract.TABLE_USER.COLUMN_NAME_WEIGHT)));
        int gspinnerpos = dataAdapter.getPosition(cursor.getString(cursor.getColumnIndex(UserContract.TABLE_USER.COLUMN_NAME_GENDER)));
        gSpinner.setSelection(gspinnerpos);
        int fspinnerpos = dataFAdapter.getPosition(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_FEET)));
        fSpinner.setSelection(fspinnerpos);
        int ispinnerpos = dataIAdapter.getPosition(cursor.getString(cursor.getColumnIndex(UserContract.TABLE_USER.COLUMN_NAME_INCHES)));
        iSpinner.setSelection(ispinnerpos);

        submitButton=(Button)findViewById(R.id.submit);
        submitButton.setText("Update");
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String feet = fSpinner.getSelectedItem().toString();
                String inches = iSpinner.getSelectedItem().toString();
                int heightfeet = 0;
                if(!(feet.equals("Choose your height by ft"))){
                    heightfeet = Integer.valueOf(feet);
                }
                int heightinches = 0;
                if(!(inches.equals("Choose your height by inches"))){
                    heightinches = Integer.valueOf(inches);
                }
                String name = "";
                if(name !=null){
                    name=nameText.getText().toString();
                }
                String gender = "M";
                if(gender==null || gender.equals("Choose your Gender")){
                    gender = gSpinner.getSelectedItem().toString();
                }
                int weight = 0;
                if(weightText.getText() != null){
                    weight = Integer.valueOf(weightText.getText().toString());
                }

                userProfile user = new userProfile(heightfeet,heightinches,name,gender,weight);
                updateUser(db,user);

                Toast.makeText(UpdateProfile.this,"Profile Updated",Toast.LENGTH_LONG).show();
                sendUser(v);
            }

        });
    }

    public void sendUser(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // TODO Auto-generated method stub
    }
    private Cursor getUserData(SQLiteDatabase db){
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
    private int updateUser(SQLiteDatabase db, userProfile user){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_NAME, user.getName());
        cv.put(COLUMN_NAME_GENDER, user.getGender());
        cv.put(COLUMN_NAME_FEET, user.getFeet());
        cv.put(COLUMN_NAME_INCHES, user.getInches());
        cv.put(COLUMN_NAME_WEIGHT, user.getWeight());

        return db.update(UserContract.TABLE_USER.TABLE_NAME, cv,null, null);
    }
}
