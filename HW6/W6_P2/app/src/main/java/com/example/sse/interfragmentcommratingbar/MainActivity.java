package com.example.sse.interfragmentcommratingbar;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity
                implements FragmentButtons.FragmentButtonsListener {

    FragmentButtons fragmentButtons;
    DrawableFragment drawableFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // create the fragment objects
        fragmentButtons = new FragmentButtons();
        drawableFragment = new DrawableFragment();

        // connect the fragments to MainActivity
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragBtn, fragmentButtons)
                .replace(R.id.fragDraw, drawableFragment).commit();
    }

    // implement the function from FragmentButtonListener Interface
    @Override
    public void fragmentButtonPressed(String button) {

        // pass the message to the drawableFragment
        drawableFragment.updateCurrentIndex(button);
        Log.i("LOG_TAG", "Host Received Button: " + button);
    }
}
