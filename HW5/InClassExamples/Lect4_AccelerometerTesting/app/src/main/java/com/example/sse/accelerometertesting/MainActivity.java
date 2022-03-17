package com.example.sse.accelerometertesting;

import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;

//Imports for hardware sensors
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private String TAG = "BOSTON";

    private float lastX, lastY, lastZ;  //old coordinate positions from accelerometer, needed to calculate delta.
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;

    private SeekBar seekBar;

    private TextView tvDeltaX;
    private TextView tvDeltaY;
    private TextView tvDeltaZ;

    // value used to determine whether user shook the device "significantly"
    private static int SIGNIFICANT_SHAKE = 1000;   //tweak this as necessary
    private CameraManager CamManager;
    private String CamID;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        df = new DecimalFormat("0.00");

        //flash

        CamManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            CamID = CamManager.getCameraIdList()[0];  //rear camera is at index 0
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


        //flash


        // initialize acceleration values
        acceleration = 0.00f;                                         //Initializing Acceleration data.
        currentAcceleration = SensorManager.GRAVITY_EARTH;            //We live on Earth.
        lastAcceleration = SensorManager.GRAVITY_EARTH;               //Ctrl-Click to see where else we could use our phone.

//
        //     setHasOptionsMenu(true);   //this lets the compiler know there are menu items

        tvDeltaX = (TextView) findViewById(R.id.tvDeltaX);
        tvDeltaY = (TextView) findViewById(R.id.tvDeltaY);
        tvDeltaZ = (TextView) findViewById(R.id.tvDeltaZ);

        seekBar = (SeekBar)findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SIGNIFICANT_SHAKE = progress;
                Log.e(TAG, "New SIG SHAKE = " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart Triggered.");
        enableAccelerometerListening();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop Triggered.");
        disableAccelerometerListening();
        super.onStop();
    }

    // enable listening for accelerometer events
    private void enableAccelerometerListening() {
        // The Activity has a SensorManager Reference.
        // This is how we get the reference to the device's SensorManager.
        SensorManager sensorManager =
                (SensorManager) this.getSystemService(
                        Context.SENSOR_SERVICE);    //The last parm specifies the type of Sensor we want to monitor


        //Now that we have a Sensor Handle, let's start "listening" for movement (accelerometer).
        //3 parms, The Listener, Sensor Type (accelerometer), and Sampling Frequency.
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);   //don't set this too high, otw you will kill user's battery.
    }



    // disable listening for accelerometer events
    private void disableAccelerometerListening() {

//Disabling Sensor Event Listener is two step process.
        //1. Retrieve SensorManager Reference from the activity.
        //2. call unregisterListener to stop listening for sensor events
        //THis will prevent interruptions of other Apps and save battery.

        // get the SensorManager
        SensorManager sensorManager =
                (SensorManager) this.getSystemService(
                        Context.SENSOR_SERVICE);

        // stop listening for accelerometer events
        sensorManager.unregisterListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // get x, y, and z values for the SensorEvent
            //each time the event fires, we have access to three dimensions.
            //compares these values to previous values to determine how "fast"
            // the device was shaken.
            //Ref: http://developer.android.com/reference/android/hardware/SensorEvent.html

            float x = event.values[0];   //obtaining the latest sensor data.
            float y = event.values[1];   //sort of ugly, but this is how data is captured.
            float z = event.values[2];

            // save previous acceleration value
            lastAcceleration = currentAcceleration;

            // calculate the current acceleration
            currentAcceleration = x * x + y * y + z * z;   //This is a simplified calculation, to be real we would need time and a square root.

            // calculate the change in acceleration        //Also simplified, but good enough to determine random shaking.
            acceleration = currentAcceleration *  (currentAcceleration - lastAcceleration);

            // if the acceleration is above a certain threshold
            if (acceleration > SIGNIFICANT_SHAKE) {
                Log.e(TAG, "delta x = " + (x-lastX));
                Log.e(TAG, "delta y = " + (y-lastY));
                Log.e(TAG, "delta z = " + (z-lastZ));
                Toast.makeText(getBaseContext(), "SIGNIFICANT SHAKE!", Toast.LENGTH_SHORT).show();
LightOn();
LightOff();


                tvDeltaX.setText(df.format(x-lastX));
                tvDeltaY.setText(df.format(y-lastY));
                tvDeltaZ.setText(df.format(z-lastZ));

//                    tvDeltaX.setText(df.format(x));
//                    tvDeltaY.setText(df.format(y));
//                    tvDeltaZ.setText(df.format(z-9.8));
            }
            else
//                    Toast.makeText(getBaseContext(), "NOT A SIGNIFICANT SHAKE!", Toast.LENGTH_LONG).show();

            lastX = x;
            lastY = y;
            lastZ = z;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    //MENU STUFF, SAVE FOR NEXT WEEK
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id =  item.getItemId();

        if(id == R.id.call_your_mom_menuitem){
            Toast.makeText(getBaseContext(), "Ring, ring, ring...", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.happy_menuitem){
            Toast.makeText(getBaseContext(), "I am a Happy Camper!", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.Lets_Goto_Aruba) {
            Toast.makeText(getBaseContext(), "It's always Sunny in Aruba.", Toast.LENGTH_LONG).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    ////Menu Inflation and Binding
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.call_your_mom_menuitem) {
//            Toast.makeText(getBaseContext(), "Ring ring, Hi Mom.", Toast.LENGTH_LONG).show();
//            return true;
//        } else if (id == R.id.happy_menuitem){
//            Toast.makeText(getBaseContext(), "You clicked the happy camper icon", Toast.LENGTH_LONG).show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    public void LightOn()
    {
        try {
            CamManager.setTorchMode(CamID, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void LightOff()
    {
        try {
            CamManager.setTorchMode(CamID, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}