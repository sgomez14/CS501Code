package com.example.w4_p4;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ChangeDirection {

    private Class north;
    private Class east;
    private Class south;
    private Class west;

    public ChangeDirection(){
        Log.d("ChangeDirection", "Created a ChangeDirection object");
        north = MainActivityNorth.class;
        east = MainActivityEast.class;
        south = MainActivitySouth.class;
        west = MainActivityWest.class;
    }

    public void goNorth(Context startContext){
        Intent intent = new Intent(startContext, north);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startContext.startActivity(intent);
    }

    public void goEast(Context startContext){
        Intent intent = new Intent(startContext, east);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startContext.startActivity(intent);

    }

    public void goSouth(Context startContext){
        Intent intent = new Intent(startContext, south);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startContext.startActivity(intent);

    }

    public void goWest(Context startContext){
        Intent intent = new Intent(startContext, west);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startContext.startActivity(intent);

    }
}
