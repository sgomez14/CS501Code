package com.example.w3_p4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String USERNAME = "s";
    private final String PASSWORD = "s";

    private String userName = "";

    private TextView tvLoginName;
    private TextView tvUser;
    private TextView tvPassword;
    private EditText edtUser;
    private EditText edtPassword;
    private Button   btnLoginSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLoginName    = (TextView) findViewById(R.id.tvLoginName   );
        tvUser         = (TextView) findViewById(R.id.tvUser        );
        tvPassword     = (TextView) findViewById(R.id.tvPassword    );
        edtUser        = (EditText) findViewById(R.id.edtUser       );
        edtPassword    = (EditText) findViewById(R.id.edtPassword   );
        btnLoginSubmit = (Button  ) findViewById(R.id.btnLoginSubmit);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        String labelLogin = tvLoginName.getText().toString();
        String labelUser  = tvUser.getText().toString();
        String labelPwd   = tvPassword.getText().toString();

        String user = edtUser.getText().toString();
        String pwd =  edtPassword.getText().toString();

        outState.putString("labelLogin", labelLogin);
        outState.putString("labelUser", labelUser);
        outState.putString("labelPwd", labelPwd);
        outState.putString("user", user);
        outState.putString("pwd", pwd);

        super.onSaveInstanceState(outState);  // Call last since this is for destructive routines
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {   //QUESTIONS: Will this always be called, if not, why not?
        super.onRestoreInstanceState(savedInstanceState);


        tvLoginName.setText(savedInstanceState.getString("labelLogin"));
        tvUser.setText(savedInstanceState.getString("labelUser"));
        tvPassword.setText(savedInstanceState.getString("labelPwd"));
        edtUser.setText(savedInstanceState.getString("user"));
        edtPassword.setText(savedInstanceState.getString("pwd"));
    }

    public void onClickLoginSubmit(View view){

        userName =  edtUser.getText().toString();
        boolean userMatches = userName.equals(USERNAME);
        boolean pwdMatches  =  edtPassword.getText().toString().equals(PASSWORD);

        if (userMatches & pwdMatches){
            // First create Intent object
            Intent GotoActivity2 = new Intent(getApplicationContext(), MainActivity2.class );

            GotoActivity2.putExtra("userName", userName);

            // Initiate Activity2
            startActivity(GotoActivity2);
            Log.d("login", "login successful");
        }
        else{
            Log.d("login", "login failed");
        }

    }
}