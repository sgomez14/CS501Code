package com.example.c2_p23;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button   btnChangeColor;
    private TextView tvLight;

    private int trackColor = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChangeColor = (Button) findViewById(R.id.btnChangeColor);
        tvLight        = (TextView) findViewById(R.id.tvLight);

        // quick and dirty way to make Textview like a circle
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(200);
        shape.setColor(Color.GREEN);
        tvLight.setBackground(shape);
    }

    public void onClickChangeColor(View view){
        if (trackColor == 1){
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(200);
            shape.setColor(Color.YELLOW);
            tvLight.setBackground(shape);
            trackColor++;
        }
        else if (trackColor == 2){
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(200);
            shape.setColor(Color.RED);
            tvLight.setBackground(shape);
            trackColor++;
        }
        else{
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(200);
            shape.setColor(Color.GREEN);
            tvLight.setBackground(shape);
            trackColor = 1;
        }
    }
}