package com.example.w6_p1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity
        implements FragmentSender.FragmentSenderListener {

    private FragmentSender sender;
    private FragmentReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sender = new FragmentSender();
        receiver = new FragmentReceiver();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentSender, sender)
                .replace(R.id.fragmentReceiver, receiver).commit();
    }


    @Override
    public void selectionMade(String msg) {
        receiver.changePicture(msg);
        receiver.updateTextView(msg);
        Log.i("LOG_TAG", "Selected Animal:" + msg);
    }
}