package com.example.w7_p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Drawable> drawables;
    private int currentDrawableIndex;
    private final String DRAWABLE_TAG = "app_";
    private Random random;
    private Button button;
    private ImageView imageView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDrawables();

        // get references to the view elements

        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        retrieveSharedPreferenceInfo();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentDrawableIndex = getRandomInt();
                Drawable drawable = drawables.get(currentDrawableIndex);
                imageView.setImageDrawable(drawable);



            }
        });
    }

    @Override
    protected void onDestroy() {
        saveSharedPreferenceInfo();

        super.onDestroy();
    }

    public int getRandomInt(){
        random = new Random(System.currentTimeMillis());
        int upperbound = drawables.size();
        return random.nextInt(upperbound);

    }

    void saveSharedPreferenceInfo(){
        //1. Refer to the SharedPreference Object.
        SharedPreferences simpleAppInfo = getSharedPreferences("MainActivityInfo", Context.MODE_PRIVATE);
        //Private means no other Apps can access this.

        //2. Create an Shared Preferences Editor for Editing Shared Preferences.
        //Note, not a real editor, just an object that allows editing...

        SharedPreferences.Editor editor = simpleAppInfo.edit();

        //3. Store what's important!  Key Value Pair, what else is new...
        editor.putInt("currentDrawableIndex", currentDrawableIndex);
        editor.putString("editText", editText.getText().toString());

        //4. Save your information.
        editor.apply();

        Toast.makeText(this, "Shared Preference Data Updated.", Toast.LENGTH_LONG).show();
    }

    void retrieveSharedPreferenceInfo(){
        SharedPreferences simpleAppInfo = getSharedPreferences("MainActivityInfo", Context.MODE_PRIVATE);
        imageView.setImageDrawable(drawables.get(simpleAppInfo.getInt("currentDrawableIndex", 0)));
        editText.setText(simpleAppInfo.getString("editText", ""));
    }


    //Quick and Dirty way to get drawable resources, we prefix with "animal_" to filter out just the ones we want to display.
//REF: http://stackoverflow.com/questions/31921927/how-to-get-all-drawable-resources
    public void getDrawables() {
        Field[] drawablesFields = com.example.w7_p2.R.drawable.class.getFields();  //getting array of ALL drawables.
        drawables = new ArrayList<>();  //we prefer an ArrayList, to store the drawables we are interested in.  Why ArrayList and not an Array here? A: _________

        String fieldName;
        for (Field field : drawablesFields) {   //1. Looping over the Array of All Drawables...
            try {
                fieldName = field.getName();    //2. Identifying the Drawables Name, eg, "animal_bewildered_monkey"
                Log.i("LOG_TAG", "com.example.w7_p2.R.drawable." + fieldName);
                if (fieldName.startsWith(DRAWABLE_TAG)){
                    //3. Adding drawable resources that have our prefix, specifically "animal_".
                    drawables.add(getResources().getDrawable(field.getInt(null)));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}