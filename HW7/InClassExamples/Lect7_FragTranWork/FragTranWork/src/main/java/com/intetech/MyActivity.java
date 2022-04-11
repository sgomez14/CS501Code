//ref: http://www.java2s.com/Code/Android/Core-Class/Demonstrationofhidingandshowingfragments.htm

package com.intetech;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyActivity extends Activity implements OnClickListener
{
    Button addButton, removeButton;

    MyFrag myFrag;

    List<MyFrag> myFragList = new ArrayList<MyFrag> ();

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_layout);
        addButton = (Button) findViewById (R.id.addButton);
        removeButton = (Button) findViewById (R.id.removeButton);
        addButton.setOnClickListener (this);      //just another way to bind onClick Events...
        removeButton.setOnClickListener (this);   //ref: https://stackoverflow.com/questions/29479647/android-setonclicklistener-vs-onclicklistener-vs-view-onclicklistener
    }

    @Override
    public void onClick (View v)  //could also be used for Early binding, ie, from the IDE
    {
        if (v.equals (addButton))
        {
            //1. grab the reference to the fragment manager
            FragmentManager fragmentManager = getFragmentManager ();
            //2. start a new fragment transaction.
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
//            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            //3. Instantiate the fragment, and identify it with a time stamp.
            myFrag = new MyFrag ();
            myFrag.setSpecialText ("Frag time:  " + System.currentTimeMillis ());
            //4. Keep track of this latest fragment, you may want to interact with it again
            myFragList.add (myFrag);
            //5. Calling the add method of the transaction
            fragmentTransaction.add (R.id.myFrame, myFrag);
            fragmentTransaction.addToBackStack (myFrag.getSpecialText());  //why do we do this?

//6. Nothing happens until you commit, just like a Database
            fragmentTransaction.commit ();
        }
        else  //removeButton clicked...
        {
            if ((myFragList.size () - 1) >= 0)  //making sure there is something to remove before we start
            {
                FragmentManager fragmentManager = getFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                MyFrag lastFrag = myFragList.get (myFragList.size () - 1);
//                MyFrag lastLastFrag = myFragList.get (myFragList.size () - 3);  //Q&D, must be at least 3 fragmentss.
//                fragmentTransaction.replace() (lastLastFrag, lastFrag);
                fragmentTransaction.remove (lastFrag);   //removes from screen.
//                fragmentTransaction.hide(lastFrag);
                myFragList.remove (lastFrag);   //removes from array list
                fragmentTransaction.commit ();
            }

        }

    }
}
