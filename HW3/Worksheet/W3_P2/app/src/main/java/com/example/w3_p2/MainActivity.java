package com.example.w3_p2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText edt1;
    private TextView tv1;
    private Button btnSayHello;

    private String msg = "Hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("eventTracker", "called on onCreate()");

        btnSayHello = (Button) findViewById(R.id.btnSayHello);
        tv1 = (TextView) findViewById(R.id.tv1);
        edt1 = (EditText) findViewById(R.id.edt1);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("eventTracker", "called on onStart()");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("eventTracker", "called on onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("eventTracker", "called on onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("eventTracker", "called on onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("eventTracker", "called on onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("eventTracker", "called on onDestroy()");
    }

    public void clickBtnSayHello(View view){

        Log.i("eventTracker", "say hello button clicked");

        tv1.setText(msg);
        edt1.setText(msg);

    }


}