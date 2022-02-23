package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

//// imports for Gesture Detection ////
import android.view.MotionEvent;
import android.view.GestureDetector;
//////////////////////////////////////

public class MainActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener {

    // Declare all GUI elements
    private LinearLayout LLMain;
    private LinearLayout LLFlash;
    private ViewGroup.LayoutParams LLParams;
    private LinearLayout.LayoutParams LLFlashParams;
    private EditText edtAction;
    private TextView tvAction;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchOnOff;

    // Declare gesture detector
    private GestureDetector GD;    //must instantiate the gesture detector


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // Instantiate gesture detector
        GD = new GestureDetector(this, this); //Context, Listener as per Constructor Doc.


        // 1: create the Views
        edtAction = new EditText(MainActivity.this); // getApplicationContext()
        tvAction = new TextView(MainActivity.this);
        switchOnOff = new Switch(MainActivity.this);

        // 2: Set Layout Parameters for Views
        LLMain = new LinearLayout(MainActivity.this);
        LLParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LLMain.setLayoutParams(LLParams);
        LLMain.setOrientation(LinearLayout.VERTICAL);


        LLFlashParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LLFlash = new LinearLayout(MainActivity.this);
        LLFlash.setLayoutParams(LLFlashParams);
        LLFlash.setOrientation(LinearLayout.HORIZONTAL);

//        switchOnOff.setLayoutParams(LLParams);
        switchOnOff.setGravity(Gravity.LEFT);
//        tvAction.setLayoutParams(LLParams);
        tvAction.setText("Flashlight");
        tvAction.setTextSize(20);
//        edtAction.setLayoutParams(LLParams);
        edtAction.setHint("Enter Action");
        edtAction.setHeight(1000);
        edtAction.setGravity(Gravity.BOTTOM);

        // 3: Place Views in a ViewGroup
        LLFlash.addView(tvAction);
        LLFlash.addView(switchOnOff);
        LLMain.addView(LLFlash);
        LLMain.addView(edtAction);

        // 4: set content view
        setContentView(LLMain);



        // Check if Flash is available on device
        boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable){
            Log.d("flash", "Flash not available on device");
        }
        else{
            Log.d("flash", "Flash is available on this device.");

        }

        // create CameraManager object to control the flash
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NewApi")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    try{
                        cameraManager.setTorchMode("0", true);
                        edtAction.setText("on");

                    }catch(CameraAccessException e){

                    }
                }else{
                    try{
                        cameraManager.setTorchMode("0", false);
                        edtAction.setText("off");

                    }catch(CameraAccessException e){

                    }
                }
            }
        });

        edtAction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("flash", "texthandler called.");
                String action = edtAction.getText().toString();

                if (action.toLowerCase().equals("on")){
                    switchOnOff.setChecked(true);
                }
                else if (action.toLowerCase().equals("off")){
                    switchOnOff.setChecked(false);
                }
                else{
                    // do nothing
                }
            }
        });


    }

    //////////////////////////////////////////////////////////////////////////
    //very important step, otherwise we won't be able to capture our touches//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);               //Our GD will not automatically receive Android Framework Touch notifications.
        // Insert this line to consume the touch event locally by our GD,
        // IF YOU DON'T insert this before the return, our GD will not receive the event, and therefore won't do anything.
        return super.onTouchEvent(event);          // Do this last, why?
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
        Log.d("flash", "detected fling event");


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
                    onSwipeRight();
                    result = true;
                }
                else{
                    onSwipeLeft();
                    result = true;
                }
            }
        }
        else{
            // up or down swipe
            if (Math.abs(deltaY) > SWIPE_THRESHOLD &&
                    Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD){
                if (deltaY > 0){
                    onSwipeDown();
                    result = true;
                }
                else{
                    onSwipeUp();
                    result = true;
                }
            }
        }

        return result;
    }

    private void onSwipeUp() {
        switchOnOff.setChecked(true);
        edtAction.setText("on");
    }

    private void onSwipeDown() {
        switchOnOff.setChecked(false);
        edtAction.setText("off");
    }

    private void onSwipeLeft() {
    }

    private void onSwipeRight() {
    }


}