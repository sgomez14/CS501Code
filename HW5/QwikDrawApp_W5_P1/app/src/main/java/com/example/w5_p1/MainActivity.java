package com.example.w5_p1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private static final int SWIPE = 1;
    private static final int SCROLL = 2;
    private static final int SWIPE_MIN_DISTANCE = 120;          //swiping thresholds...
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int SCROLL_MIN_DISTANCE = 100;
    private static final int SCROLL_MAX_OFF_PATH = 120;
    private static final int SCROLL_THRESHOLD_VELOCITY = 1000;
    private static final String DRAWABLE_PREFIX = "index_";  //used to prefix images we want to show in our app.

    private ArrayList<Drawable> drawables;
    private int currPictureIndex;  //keeping track of which drawable is currently displayed.

    private ImageView imgView;
    private GestureDetector GD; //consumes gesture events.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = (ImageView) findViewById(R.id.imgView);
        GD = new GestureDetector(this, this);
        currPictureIndex = 0;
        getDrawables();
        imgView.setImageDrawable(null); //Clearing out the default image from design time.
        changePicture();        //Sets the ImageView to the first drawable in the list.

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
    public boolean onScroll(MotionEvent startEvent, MotionEvent endEvent,
                            float velocityX, float velocityY) {

        if (Math.abs(startEvent.getX() - endEvent.getX()) > SCROLL_MAX_OFF_PATH) {  return false;  }

        /* (0,0) is top left corner of phone.
        therefore, positive value means right to left direction */
        final float distance = startEvent.getX() - endEvent.getX();
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
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}