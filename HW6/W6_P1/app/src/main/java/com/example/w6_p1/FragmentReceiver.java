package com.example.w6_p1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentReceiver#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReceiver extends Fragment {

    ArrayList<Drawable> drawables;  //keeping track of our drawables
    private final int OWL = 0;
    private final int LION = 1;
    private final int FROG = 2;
    private final int ELEPHANT = 3;
    private final int CHIMPANZEE = 4;
    private final String drawablePrefix = "app_";

    // for the GUI elements
    private ImageView imgView;
    private TextView TV_Msg;

    // to play the animal sounds
    private MediaPlayer mediaPlayer;

    public FragmentReceiver() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_receiver, container, false);
        imgView = (ImageView) v.findViewById(R.id.imgView);
        TV_Msg = (TextView) v.findViewById(R.id.TV_Msg);
        return v;
    }

    public void updateTextView(String msg){
        TV_Msg.setText(msg);
    }

    //Routine to change the picture in the image view dynamically.
    public void changePicture(String animal) {
        // get the IDs for the pictures
        int drawableResId = this.getResources().getIdentifier(drawablePrefix+animal,
                "drawable", getActivity().getPackageName());

        // get the IDs for the sounds
        int rawResId = this.getResources().getIdentifier(animal, "raw",
                getActivity().getPackageName());

        // initiate the mediaplayer
        mediaPlayer = MediaPlayer.create(getContext(), rawResId);
        mediaPlayer.start();

        // set the image
        imgView.setImageResource(drawableResId);

        Log.i("LOG_TAG", "Animal Passed to Receiver: " + animal);
    }

}