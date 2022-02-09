package com.example.passwordmatching;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText pwdOne;
    private EditText pwdTwo;
    private Button checkBtn;
    private TextView msg;
    String pwd1,pwd2;
    String msg1 = "THANK YOU!";
    String msg2 = "PASSWORDS MUST MATCH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pwdOne = (EditText) findViewById(R.id.pwdOne);
        pwdTwo = (EditText) findViewById(R.id.pwdTwo);
        checkBtn = (Button) findViewById(R.id.checkBtn);
        msg = (TextView) findViewById(R.id.msg);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd1 = pwdOne.getText().toString();
                pwd2 = pwdTwo.getText().toString();
                if(pwd1.equals(pwd2)){
                    msg.setText(msg1);
                }
                else {
                    msg.setText(msg2);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}