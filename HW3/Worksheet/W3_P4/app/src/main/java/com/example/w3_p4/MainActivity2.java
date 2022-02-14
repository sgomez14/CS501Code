package com.example.w3_p4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundleAct1 = getIntent().getExtras();
        String userName = bundleAct1.getString("userName");

        String toastMsg = "Welcome " + userName;

        Toast.makeText(MainActivity2.this, toastMsg, Toast.LENGTH_LONG).show();
    }

    public class Flashcard{
        public int operand1;
        public int operand2;
        public int answer;
        public boolean isAdd;

        public Flashcard(){
            Random random = new Random(System.currentTimeMillis());
            isAdd = random.nextInt(100) < 50;

            operand1 = random.nextInt(100)+1; // range [1,99]
            operand2 = random.nextInt(20)+1; // range [1,19]

            calculate();

        }


        public void calculate(){
            if (isAdd){
                answer = operand1 + operand2;
            }
            else {
                answer = operand1 - operand2;
            }

        }

    }
}

