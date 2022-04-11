package com.intetech;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyFrag extends Fragment
{
    private
      String specialText;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate (R.layout.frag_layout, container,false);
        TextView textView= (TextView) view.findViewById (R.id.my_frag_txt);
        textView.setText (specialText);
        Toast.makeText(getActivity(),"Fragment Created...", Toast.LENGTH_SHORT ).show();
        return view;
    }

//    @Override
//    public void onDetach() {
//        Toast.makeText(getActivity(),"Fragment Detached...", Toast.LENGTH_SHORT ).show();
//        super.onDetach();
//    }

    public String getSpecialText ()
    {
        return specialText;
    }

    public void setSpecialText (String specialText)
    {
        this.specialText = specialText;
    }


    
}
