package com.zeyu.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.Math;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor acceSensor;
    private boolean acceSensorAvail = false, firstMove = true;
    private float currX, currY, currZ, lastX, lastY, lastZ, diffX, diffY, diffZ;
    private float THRESHOLD = 1f;
    private Button BTN_start, BTN_stop;
    private TextView TV_numStep;
    private ListView LV_Activity;
    private ArrayAdapter<String> adapter;
    private String selection;
    private final float EASY = 5f, MEDIUM = 7f, HARD = 9f;
    private MediaPlayer mediaPlayer;
    private int shakeCounter = 0;
    private boolean stopPressed = false, blinking = false, songPlaying = false;
    private Thread flashlightThread, supermanThread, CoFThread, RockyThread;
    private long startTime;
    private final long TWO_MINUTE = (long) (1.2 * Math.pow(10, 11));
    private final long FIVE_MINUTE = (long) (3 * Math.pow(10, 11));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER )!= null){
            acceSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            acceSensorAvail = true;
        }
        LV_Activity = findViewById(R.id.LV_activity);
        BTN_start = findViewById(R.id.BTN_start);
        BTN_start.setEnabled(false);
        BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPressed = false;
                BTN_start.setEnabled(false);
                BTN_stop.setEnabled(true);
                LV_Activity.setEnabled(false);
                shakeCounter = 0;
                TV_numStep.setText(shakeCounter+" steps");
                if(acceSensorAvail) sm.registerListener(MainActivity.this, acceSensor, SensorManager.SENSOR_DELAY_NORMAL);
                startTime = System.nanoTime();
                firstMove = true;
            }

        });
        BTN_stop = findViewById(R.id.BTN_stop);
        BTN_stop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                stopPressed = true;
                blinking = false;
                songPlaying = false;
                BTN_start.setEnabled(false);
                BTN_stop.setEnabled(false);
                if(mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.stop();
                LV_Activity.setEnabled(true);
                shakeCounter = 0;
                TV_numStep.setText("number of steps");
                supermanThread.interrupt();
                if(acceSensorAvail) sm.unregisterListener(MainActivity.this);
                flashlightOnOff(false);
            }
        });
        BTN_stop.setEnabled(false);
        TV_numStep = findViewById(R.id.TV_numSteps);
        ArrayList<String> list = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        LV_Activity.setAdapter(adapter);
        list.add("Easy");
        list.add("Medium");
        list.add("Hard");
        adapter.notifyDataSetChanged();
        LV_Activity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selection = LV_Activity.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this , "You've selected "+selection+"!", Toast.LENGTH_LONG).show();
                TV_numStep.setText(shakeCounter+" steps");
                BTN_start.setEnabled(true);
            }
        }
        );

        flashlightThread = new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run(){
                boolean on = true;
                while(shakeCounter < 100){
                    if(stopPressed) {
                        blinking = false;
                        return;
                    }
                    on = on ? false : true;
                    blinking = true;
                    flashlightOnOff(on);
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException e){}
                }
            }
        };

        supermanThread = new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run(){
                songPlaying = true;
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.superman);
                mediaPlayer.start();
            }
        };

        CoFThread = new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run(){
                songPlaying = true;
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.cof);
                mediaPlayer.start();
            }
        };

        RockyThread = new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run(){
                songPlaying = true;
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.rocky);
                mediaPlayer.start();
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void endWorkout(){
        stopPressed = true;
        blinking = false;
        songPlaying = false;
        shakeCounter = 0;
        if(mediaPlayer.isPlaying()) mediaPlayer.stop();
        long time =  System.nanoTime() - startTime;
        if (time < TWO_MINUTE) {
            Toast.makeText(MainActivity.this , "You are a Rockstar", Toast.LENGTH_LONG).show();
        }
        else if(time > FIVE_MINUTE){
            Toast.makeText(MainActivity.this , "Great job, keep practicing to get faster.", Toast.LENGTH_LONG).show();
        }
        BTN_start.setEnabled(false);
        BTN_stop.setEnabled(false);
        LV_Activity.setEnabled(true);
        if(acceSensorAvail) sm.unregisterListener(MainActivity.this);
        flashlightOnOff(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void flashlightOnOff(boolean signal){
        CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            String id = cm.getCameraIdList()[0];
            cm.setTorchMode(id, signal);
        }catch(CameraAccessException e){

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currX = sensorEvent.values[0];
        currY = sensorEvent.values[1];
        currZ = sensorEvent.values[2];

        if(!firstMove){
             diffX = Math.abs(currX - lastX);
             diffY = Math.abs(currY - lastY);
             diffZ = Math.abs(currZ - lastZ);
             if(selection.equals("Easy")) THRESHOLD = EASY;
                     else if(selection.equals("Medium")) THRESHOLD = MEDIUM;
                     else if(selection.equals("Hard")) THRESHOLD = HARD;
              if(diffX > THRESHOLD && diffY > THRESHOLD ||
                      diffX > THRESHOLD && diffZ > THRESHOLD ||
                      diffZ > THRESHOLD && diffY > THRESHOLD){
                shakeCounter++;
                TV_numStep.setText(shakeCounter+" steps");
                if(shakeCounter == 100){
                    endWorkout();
                    return;
                }
                else if(selection.equals("Easy") && shakeCounter >= 10){
                    if(!blinking){
                        flashlightThread.start();
                    }
                }
                else if((selection.equals("Medium") || selection.equals("Hard")) && shakeCounter >= 30){
                    if(!blinking){
                        flashlightThread.start();
                    }
                }
                if(selection.equals("Easy") && shakeCounter >= 30){
                    if(!songPlaying){
                        supermanThread.start();
                    }
                }
                else if(selection.equals("Medium") && shakeCounter >= 45){
                    if(!songPlaying){
                        CoFThread.start();
                    }
                }
                else if(selection.equals("Hard") && shakeCounter >= 60){
                    if(!songPlaying){
                        RockyThread.start();
                    }
                }
              }
        }
        lastX = currX;
        lastY = currY;
        lastZ = currZ;
        firstMove = false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}