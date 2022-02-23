package com.example.w4_p4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivityEast extends AppCompatActivity
        implements GestureDetector.OnGestureListener {

    // Declare gesture detector
    private GestureDetector GD;    //must instantiate the gesture detector

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_east);

        // Instantiate gesture detector
        GD = new GestureDetector(this, this); //Context, Listener as per Constructor Doc.
    }

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
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent startEvent, MotionEvent endEvent,
                           float velocityX, float velocityY) {
        Log.d("fling", "detected fling event");


        boolean result = false;
        final double SWIPE_THRESHOLD = 100;
        final double SWIPE_VELOCITY_THRESHOLD = 100;
        double deltaY = endEvent.getY() - startEvent.getY();
        double deltaX = endEvent.getX() - startEvent.getX();

        // which was greater? movement across Y or X?
        if (Math.abs(deltaX) > Math.abs(deltaY)){
            // right or left swipe
            if (Math.abs(deltaX) > SWIPE_THRESHOLD &&
                    Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                if (deltaX > 0){
                    // onSwipeRight, do nothing since already on East activity
                    // MainActivity.changeDirection.goEast(getApplicationContext());
                    result = true;
                }
                else{
                    // onSwipeLeft
                     MainActivity.changeDirection.goWest(getApplicationContext());
                    result = true;
                }
            }
        }
        else{
            // up or down swipe
            if (Math.abs(deltaY) > SWIPE_THRESHOLD &&
                    Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD){
                if (deltaY > 0){
                    // onSwipeDown
                    MainActivity.changeDirection.goSouth(getApplicationContext());
                    result = true;
                }
                else{
                    // onSwipeUp
                    MainActivity.changeDirection.goNorth(getApplicationContext());
                    result = true;
                }
            }
        }

        return result;
    }
}