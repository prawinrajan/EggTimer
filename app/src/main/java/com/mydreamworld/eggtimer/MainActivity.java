package com.mydreamworld.eggtimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekbar;
    TextView timerTextview;
    Boolean counterIsActive=false;
    Button controllbutton;
    CountDownTimer countDownTimer;
    public void resetTimer(){
        timerTextview.setText("0:30");
        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        controllbutton.setText("Go!");
        timerSeekbar.setEnabled(true);
        counterIsActive=false;
    }
    public void updateTimer(int secondsLeft){
        int minutes =(int)secondsLeft/60;
        int seconds =  secondsLeft - minutes*60;
        String secondString =Integer.toString(seconds);

        if (seconds<=9){
            secondString="0"+secondString;
        }


        timerTextview.setText(Integer.toString(minutes)+":"+secondString);

    }
    public void controllTimer(View view){
        if (counterIsActive==false) {

            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            controllbutton.setText("Stop");

          countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.audio);
                    mediaPlayer.start();
                }
            }.start();
        }else {
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekbar=(SeekBar) findViewById(R.id.timerSeekbar);
        timerTextview = (TextView) findViewById(R.id.textView);
        controllbutton = (Button) findViewById(R.id.controllbutton);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
