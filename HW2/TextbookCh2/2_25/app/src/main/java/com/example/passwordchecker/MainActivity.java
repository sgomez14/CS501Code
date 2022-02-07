package com.example.passwordchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Declare EditText objects for passwords
    private EditText etpPassword1;
    private EditText etpPassword2;

    // Declare Button object
    private Button btnCheckPwd;

    // Declare Textview object
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate EditText objects
        etpPassword1 = (EditText) findViewById(R.id.etpPassword1);
        etpPassword2 = (EditText) findViewById(R.id.etpPassword2);

        // Instantiate Button object
        btnCheckPwd = (Button) findViewById(R.id.btnCheckPwd);

        // Instantiate TextView object
        tvMsg = (TextView) findViewById(R.id.tvMsg);

    }

    public void checkPassword(View view){
        String pwd1 = etpPassword1.getText().toString();
        String pwd2 = etpPassword2.getText().toString();

        // String variables for messages
        String successMsg = "THANK YOU";
        String failMsg    = "PASSWORDS MUST MATCH";

        // If the passwords match display success message
        if (pwd1.equals(pwd2)){
            // #07b519 is a green color
            tvMsg.setTextColor(Color.parseColor("#07b519"));
            tvMsg.setText(successMsg);

        }
        else {
            // #b52407 is red color
            tvMsg.setTextColor(Color.parseColor("#b52407"));
            tvMsg.setText(failMsg);
        }
    }
}