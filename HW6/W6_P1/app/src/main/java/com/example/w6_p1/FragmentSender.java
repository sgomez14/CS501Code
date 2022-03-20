package com.example.w6_p1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSender#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSender extends Fragment {


    // Listview items
    private ListView LV_Animals;
    private ArrayList<String> listAnimals;
    private ArrayAdapter<String> adapter;
    private String selection;

    // EditText for sending message
    private EditText edtMsg;


    public FragmentSender() {
        // Required empty public constructor
    }


    public interface FragmentSenderListener{
        public void selectionMade(String msg);
    }

    // create the listener object for communicating with host
    FragmentSenderListener FSL;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        FSL = (FragmentSenderListener) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //MUST HAPPEN FIRST, otherwise components don't exist.
        View v =inflater.inflate(R.layout.fragment_sender, container, false);

        // Setup for ListView
        LV_Animals = (ListView) v.findViewById(R.id.LV_Animals);
        listAnimals = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listAnimals);
        LV_Animals.setAdapter(adapter);
        listAnimals.add("owl");
        listAnimals.add("lion");
        listAnimals.add("frog");
        listAnimals.add("elephant");
        listAnimals.add("chimpanzee");
        adapter.notifyDataSetChanged();

        LV_Animals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
               selection = LV_Animals.getItemAtPosition(position).toString();

               // Send message of which animal was selected
               FSL.selectionMade(selection);
           }
       }
       );

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}