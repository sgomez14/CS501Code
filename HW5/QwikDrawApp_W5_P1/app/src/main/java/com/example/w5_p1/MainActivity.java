package com.example.w5_p1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    // value used to determine whether user shook the device "significantly"
    private static int SIGNIFICANT_SHAKE = 20000;   //tweak this as necessary
    private static final int SWIPE = 1;
    private static final int SCROLL = 2;
    private static final int SWIPE_MIN_DISTANCE = 50;          //swiping thresholds...
    private static final int SWIPE_MAX_OFF_PATH = 85;
    private static final int SWIPE_THRESHOLD_VELOCITY = 3000;
    private static final int SCROLL_MIN_DISTANCE = 150; // scrolling thresholds
    private static final int SCROLL_MAX_OFF_PATH = 185;
    private static final int SCROLL_THRESHOLD_VELOCITY = 200;
    private static final String DRAWABLE_PREFIX = "app_";  //used to prefix images we want to show in our app.

    private ArrayList<Drawable> drawables;
    private int currPictureIndex;  //keeping track of which drawable is currently displayed.

    // Gesture Detection
    private ImageView imgView;
    private TextView tvNum;
    private GestureDetector GD; //consumes gesture events.

    // Acceleration Variables
    private String TAG = "ACCELERATION";
    private float lastX, lastY, lastZ;  //old coordinate positions from accelerometer, needed to calculate delta.
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;

    //Animations
    private Animation rotate_slide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = (ImageView) findViewById(R.id.imgView);
        tvNum = (TextView) findViewById(R.id.tvNum);
        GD = new GestureDetector(this, this);
        currPictureIndex = 0;
        getDrawables();
        imgView.setImageDrawable(null); //Clearing out the default image from design time.
        changePicture();        //Sets the ImageView to the first drawable in the list.

        rotate_slide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate_slide_off);

        // implement the listener for animation life cycle
        rotate_slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                NavRight(SCROLL);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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

    public void getDrawables() {
        Field[] drawablesFields = com.example.w5_p1.R.drawable.class.getFields();  //getting array of ALL drawables.
        drawables = new ArrayList<>();  //we prefer an ArrayList, to store the drawables we are interested in.  Why ArrayList and not an Array here? A: _________

        String fieldName;
        for (Field field : drawablesFields) {   //1. Looping over the Array of All Drawables...
            try {
                fieldName = field.getName();    //2. Identifying the Drawables Name, eg, "animal_bewildered_monkey"
                Log.i("LOG_TAG", "com.your.project.R.drawable." + fieldName);
                if (fieldName.startsWith(DRAWABLE_PREFIX))  //3. Adding drawable resources that have our prefix, specifically "animal_".
                    drawables.add(getResources().getDrawable(field.getInt(null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Routine to change the picture in the image view dynamically.
    public void changePicture() {
        //note, this is the preferred way of changing images, don't worry about parent viewgroup size changes.
        imgView.setImageDrawable(drawables.get(currPictureIndex));
        tvNum.setText(Integer.toString(currPictureIndex));
    }

    // enable listening for accelerometer events
    @RequiresApi(api = Build.VERSION_CODES.M)
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

                NavRight(SCROLL);



            }
            else
                lastX = x;
                lastY = y;
                lastZ = z;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    //////////////////////////////////////////////////////////////////////////
    //very important step, otherwise we won't be able to capture our touches//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);               //Our GD will not automatically receive Android Framework Touch notifications.
        // Insert this line to consume the touch event locally by our GD,
        // IF YOU DON'T insert this before the return, our GD will not receive the event, and therefore won't do anything.
        return super.onTouchEvent(event);
    }
    //////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent startEvent, MotionEvent endEvent,
                            float velocityX, float velocityY) {

        // limit the scrolling distance
        if (Math.abs(startEvent.getX() - endEvent.getX()) > SCROLL_MAX_OFF_PATH) {  return false;  }

        /* (0,0) is top left corner of phone.
        therefore, positive value means right to left direction */
        final float distance = startEvent.getX() - endEvent.getX();

        // to distinguish from swiping, scroll should be below a particular threshold
        final boolean enoughSpeed = Math.abs(velocityX) < SCROLL_THRESHOLD_VELOCITY;

        if (distance > SCROLL_MIN_DISTANCE && enoughSpeed){
            // right to left scroll
            NavRight(SCROLL);
            return true;
        }
        else if (distance < -SCROLL_MIN_DISTANCE && enoughSpeed){
            // left to right scroll
            NavLeft(SCROLL);
            return true;
        }
        else {
            // ignore
            return false;
        }
    }

    private void NavRight(int Action) {

        if (Action == SWIPE){
            if (currPictureIndex >= 7){
                currPictureIndex = (currPictureIndex + 3) - 10;
            }
            else {
                currPictureIndex += 3;
            }
        }
        else if (Action == SCROLL){
            if (currPictureIndex == drawables.size() - 1)
                currPictureIndex = 0;
            else
                currPictureIndex++;
        }

        changePicture();
    }

    private void NavLeft(int Action) {

        if (Action == SWIPE){
            if (currPictureIndex <= 2 ){
                currPictureIndex = currPictureIndex + 7;
            }
            else {
                currPictureIndex -= 3;
            }
        }
        else if (Action == SCROLL){
            if (currPictureIndex == 0)
                currPictureIndex = drawables.size() - 1;
            else
                currPictureIndex--;
        }

        changePicture();
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent startEvent, MotionEvent endEvent,
                           float velocityX, float velocityY) {

        //
        if (Math.abs(startEvent.getY() - endEvent.getY()) > SWIPE_MAX_OFF_PATH) {  return false;  }


        /* (0,0) is top left corner of phone.
        therefore, positive value means right to left direction */
        final float distance = startEvent.getX() - endEvent.getX();
        final boolean enoughSpeed = Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY;

        // to distinguish from scroll, swipe should be above a particular threshold
        if (distance > SWIPE_MIN_DISTANCE && enoughSpeed){
            // right to left scroll
            NavRight(SWIPE);
            return true;
        }
        else if (distance < -SWIPE_MIN_DISTANCE && enoughSpeed){
            // left to right scroll
            NavLeft(SWIPE);
            return true;
        }
        else {
            // ignore
            return false;
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        // apply animation to both the image view and text view
        imgView.startAnimation(rotate_slide);
        tvNum.startAnimation(rotate_slide);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }
}