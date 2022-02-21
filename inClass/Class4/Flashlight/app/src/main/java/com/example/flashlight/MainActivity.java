package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtAction = new EditText(MainActivity.this);

        TextView tvAction = new TextView(MainActivity.this);

        Switch switchOnOff = new Switch(MainActivity.this);

        LinearLayout container = new LinearLayout(getApplicationContext());
        final ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container.setLayoutParams(params);

        // Implement view group for each thing here

        container.addView(edtAction);
        container.addView(tvAction);
        container.addView(switchOnOff);


        container.addView(container);



//        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
//
//        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @SuppressLint("NewApi")
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//                    try{
//                        cameraManager.setTorchMode("0", false);
//
//                    }catch(CameraAccessException e){
//
//                    }
//                }else{
//                    try{
//                        cameraManager.setTorchMode("0", true);
//
//                    }catch(CameraAccessException e){
//
//                    }
//                }
//            }
//        });


    }




}