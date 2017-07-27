package com.example.fugibeast.countdowndemo;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    Button startBtn;
    TextView timerView;
    TextToSpeech textToSpeech;
    EditText timeSet;
    int time = 0;

    private int MY_DATA_CHECK_CODE = 0;
    Boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = (Button)findViewById(R.id.startBtn);
        timerView = (TextView) findViewById(R.id.timerView);
        timeSet = (EditText) findViewById(R.id.timeSet);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimer countDownTimer = new CountDownTimer(Integer.valueOf(timeSet.getText().toString())*1000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(millisUntilFinished < 6000 && flag == true){
                            speak("5 seconds left");
                            flag = false;
                        }
                        if(millisUntilFinished<4000){
                            speak(millisUntilFinished / 1000 + "");
                        }
                        timerView.setText(""+millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        speak("Done");
                        timerView.setText("Done");
                    }
                }.start();
            }
        });
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
        textToSpeech.speak(text,TextToSpeech.QUEUE_ADD,null);
    }
}
