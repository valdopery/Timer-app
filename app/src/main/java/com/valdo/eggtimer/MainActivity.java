package com.valdo.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    SeekBar skb;
    MediaPlayer mp;
    Boolean counterIsActive=false;
    Button controller;
    CountDownTimer countdt;


     //creating this method because we will have to use this code more than once
     public void updateTimer(int secondsLeft){

         //converting seconds into minutes casting so that the seconds will be ignored
         int minutes=(int)secondsLeft/60;
         //getting the previously ignored seconds (Total  seconds- minutes(in seconds0))
         int seconds=secondsLeft-minutes*60;
         //creating a string that will store the seconds
         String secondString=String.valueOf(seconds);
          if(seconds<=9){
                 secondString="0"+secondString;
             }



         txt.setText(String.valueOf(minutes)+":"+secondString);
     }

      //cretaing a reset method
    public void resetButton(){
        txt.setText("0:30");
        skb.setProgress(30);
        skb.setEnabled(true);
        countdt.cancel();
        controller.setText("GO!");
        counterIsActive = false;
    }

     //creating  a method responsible for the count down operation
     public void controlTimer(View view){
         if(counterIsActive==false) {
             counterIsActive = true;
             skb.setEnabled(false);
             controller.setText("Stop");
            countdt= new CountDownTimer(skb.getProgress() * 1000, 100) {
                 @Override
                 public void onTick(long millisUntilFinished) {
                     //(int)millisUntilFinished/1000 is firstly counting down and we use (int) just to cast so that we can send
                     //to the updateTimer method
                     updateTimer((int) millisUntilFinished / 1000);
                 }

                 @Override
                 public void onFinish() {
                     txt.setText("0:00");
                     mp = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                     mp.start();
                     resetButton();

                 }
             }.start();
         }else{
             resetButton();

         }
     }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         skb=(SeekBar)findViewById(R.id.seekBar);
         txt=(TextView)findViewById(R.id.timerText) ;
         controller=(Button)findViewById(R.id.controller);
        skb.setMax(600);
        skb.setProgress(30);
        skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        }


        );













    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
