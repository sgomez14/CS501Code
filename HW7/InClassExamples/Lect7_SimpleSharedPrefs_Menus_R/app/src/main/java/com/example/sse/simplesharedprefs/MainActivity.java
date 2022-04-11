package com.example.sse.simplesharedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnClickMe;
    private EditText edtText;
    private TextView txtView;

    private Button btnSave;
    private Button btnRetrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        btnClickMe = (Button) findViewById(R.id.btnClickMe);
        edtText = (EditText) findViewById(R.id.edtText);
        txtView = (TextView) findViewById(R.id.txtView);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnRetrieve = (Button) findViewById(R.id.btnRetrieveSharedPrefs);

        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtText.setText("I always seem to forget what just happened.");
                txtView.setText("I always seem to forget what just happened.");
 //               btnClickMe.setText("Stop Clicking Me.");
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferenceInfo();
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveSharedPreferenceInfo();
            }
        });
    }


//SHARED PREFERENCE STUFF
    void saveSharedPreferenceInfo(){
        //1. Refer to the SharedPreference Object.
           SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);
           //Private means no other Apps can access this.

        //2. Create an Shared Preferences Editor for Editing Shared Preferences.
        //Note, not a real editor, just an object that allows editing...

           SharedPreferences.Editor editor = simpleAppInfo.edit();

        //3. Store what's important!  Key Value Pair, what else is new...
//           editor.putString("btnClickMe", btnClickMe.getText().toString());
           editor.putString("edtText", edtText.getText().toString());
//           editor.putString("txtView", txtView.getText().toString());

        //4. Save your information.
           editor.apply();

        Toast.makeText(this, "Shared Preference Data Updated.", Toast.LENGTH_LONG).show();
    }


    void retrieveSharedPreferenceInfo(){

        //Todo, exception handling likely needed, just in case ActivityOneInfo doesn't exit.
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);

//        String s1 = simpleAppInfo.getString("btnClickMe", "<missing>");
//        String s2 = simpleAppInfo.getString("edtText", "<missing>");
//        String s3 = simpleAppInfo.getString("txtView", "<missing>");

        //Retrieving data from shared preferences hashmap.
//        btnClickMe.setText(simpleAppInfo.getString("btnClickMe", "<missing>"));  //The second parm is the default value, eg, if the value doesn't exist.
        edtText.setText(simpleAppInfo.getString("edtText", "Data Not Found."));        //Shared Preferences use internal memory, not SD.
//        txtView.setText(simpleAppInfo.getString("txtView", "<missing>"));
//

//        Toast.makeText(this, s1, Toast.LENGTH_LONG).show();


    }

//MENU STUFF...

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        //Unfortunately there is no full blown menu editor, eg, like the layout or strings editor in the IDE.
        //Must enter the menu items manually.
        getMenuInflater().inflate(R.menu.simple_test_menu, menu);

        return true;  //we've handled it!
    }

    //Override onOptionsItemSelected(..) to handle menu clicks by the user
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.someID1) {
            Toast.makeText(getBaseContext(), "Ring ring, Hi Some ID1.", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.someID2) {
            Toast.makeText(getBaseContext(), "Ring ring, Hi Some ID2.", Toast.LENGTH_LONG).show();
            return true;
        }

//            …
        return super.onOptionsItemSelected(item);  //default behavior (fall through), shouldn't really get here tho, why?

        }
}



