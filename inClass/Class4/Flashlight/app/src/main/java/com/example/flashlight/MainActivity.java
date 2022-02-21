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
    private ViewGroup.LayoutParams LLParams;
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
        EditText edtAction = new EditText(MainActivity.this); // getApplicationContext()
        TextView tvAction = new TextView(MainActivity.this);
        Switch switchOnOff = new Switch(MainActivity.this);

        // 2: Set Layout Parameters for Views
//        LLMain = (LinearLayout)  findViewById(R.id.LLMain); // This one exists because we gave layout ID in activity_main.xml
        LLMain = new LinearLayout(MainActivity.this);
        LLParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LLMain.setLayoutParams(LLParams);
        LLMain.setOrientation(LinearLayout.VERTICAL);
//        switchOnOff.setLayoutParams(LLParams);
        switchOnOff.setGravity(Gravity.CENTER);
        tvAction.setLayoutParams(LLParams);
        tvAction.setText("Flashlight");
        tvAction.setTextSize(20);
        edtAction.setLayoutParams(LLParams);
        edtAction.setHint("Enter Action");

        // 3: Place Views in a ViewGroup
        LLMain.addView(tvAction);
        LLMain.addView(switchOnOff);
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

                    }catch(CameraAccessException e){

                    }
                }else{
                    try{
                        cameraManager.setTorchMode("0", false);

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
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d("flash", "detected fling event");

        if (this.switchOnOff == null){
            try {
                throw new Exception("Got switchOnOff is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            this.switchOnOff.toggle();
        }


//        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
//
//        try{
//            cameraManager.setTorchMode("0", true);
//
//        }catch(CameraAccessException e){}
//
//
        return true;
    }





}