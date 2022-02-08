package com.example.emailvalidator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // Declare GUI objects
    private EditText etEmail;
    private TextView tvMsg;
    private Button   btnValidateEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate GUI objects
        etEmail = (EditText) findViewById(R.id.etEmail);
        tvMsg   = (TextView) findViewById(R.id.tvMsg);
        btnValidateEmail = (Button) findViewById(R.id.btnValidateEmail);

    }

    public void validateEmail(View view){
        // Email regex example: https://howtodoinjava.com/java/regex/java-regex-validate-email-address/

        // Get email
        String email = etEmail.getText().toString();

        // Define regex based on RFC 5322
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        //"^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        // Define Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Define Matcher object
        Matcher matcher = pattern.matcher(email);

        // String variables for messages
        String successMsg = "VALID";
        String failMsg    = "INVALID";

        // If the passwords match display success message
        if (matcher.matches()){
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