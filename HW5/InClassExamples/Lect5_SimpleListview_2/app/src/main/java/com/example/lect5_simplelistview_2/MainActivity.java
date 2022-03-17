package com.example.lect5_simplelistview_2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    MediaPlayer mp;

    private ListView lvMenu;
    //   private ListAdapter laMenu;
    //   private  String MenuItems[] = {"Hamburger", "Pizza", "Chicken"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//Playing Sounds, EZ as 1-2
//ref: https://www.fesliyanstudios.com/royalty-free-sound-effects-download/cow-264

//1. Instantiate a MediaPlayer Object, passing the audio to play as a parm.
        mp = MediaPlayer.create(MainActivity.this, R.raw.dog);

//2. Start the player. DONE!
        mp.start();


//Creating a Simple ListView
// EZ as 1-2-3 (4)

//1. get the reference to your ListView
        lvMenu = (ListView) findViewById(R.id.lvMenu);  //UI

//2. Create an Adapter to bind to your ListView.
        final String[] Animals = {"Dog", "Cow", "Turkey", "Chimp", "Horse", "Lion", "Chipmunk"};  //Raw Data, array of strings to put into our ListAdapter.
        //ArrayAdapter is the interface, the go between, between UI and Data
        ArrayAdapter AnimalListAdapter = new ArrayAdapter<String>(MainActivity.this,           //Context
                android.R.layout.simple_list_item_1, //type of list (simple)
                Animals);                            //Data for the list
        //We will see much more complex Adapters as we go.
//3. ListViews work (display items) by binding themselves to an adapter.
        lvMenu.setAdapter(AnimalListAdapter);    //Let's put some things in our simple listview by binding it to our adaptor.

// 4. Create an onClick Handler.  Not for the ListView, but for its items!
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Animal;
                //  Animal = Animals[position];  //Note, This is much simpler,
                //Q (for above): Why is referring to the original array less preferable then using CallBack Parms (below)? A: _____________
                Animal = String.valueOf(parent.getItemAtPosition(position));  //Parent refers to the parent of the item, the ListView.  position is the index of the item clicked.
                if (Animal == "Cow") {
                    mp = MediaPlayer.create(MainActivity.this, R.raw.cowmooing);
                    mp.start();
                }
                else if (Animal == "Dog")
                {
                    mp = MediaPlayer.create(MainActivity.this, R.raw.dog);
                    mp.start();
                }
                else if (Animal == "Chimp")
                {
                    mp = MediaPlayer.create(MainActivity.this, R.raw.monkey);
                    mp.start();
                }
                else if (Animal == "Lion")
                {
                    mp = MediaPlayer.create(MainActivity.this, R.raw.lion);
                    mp.start();
                }


                Toast.makeText(MainActivity.this, "You Clicked on "  + Animal, Toast.LENGTH_LONG).show();
            }
        });

    }
}
