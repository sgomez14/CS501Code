package com.example.sse.interfragmentcommratingbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentButtons extends Fragment {


    public interface FragmentButtonsListener{
        public void fragmentButtonPressed(String button);
    }

    private Button btnLeft;
    private Button btnRight;
    FragmentButtonsListener listener;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        listener = (FragmentButtonsListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buttons, container, false);

        btnLeft = (Button) v.findViewById(R.id.btnLeft);
        btnRight = (Button) v.findViewById(R.id.btnRight);


        //setting up navigation call backs.  (Left and Right Buttons)
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.fragmentButtonPressed("left");
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.fragmentButtonPressed("right");
            }
        });

        return v;
    }
}