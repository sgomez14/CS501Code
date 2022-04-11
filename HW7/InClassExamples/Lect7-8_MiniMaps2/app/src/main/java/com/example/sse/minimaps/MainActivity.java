//Ref: A. Porter, U. Maryland, with a few tweaks.
//https://github.com/aporter/coursera-android/blob/master/Examples/MapLocationFromContacts/src/course/examples/maplocationfromcontacts/MapLocationFromContactsActivity.java

package com.example.sse.minimaps;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // It does get tiring typing ContactsContract.Data... over and over again.
    // These variables are shorthand aliases for data items in Contacts-related database tables
    // Java does not have a with clause. :(
//MINIMAPS START
    private static final String DATA_MIMETYPE = ContactsContract.Data.MIMETYPE;
    private static final Uri DATA_CONTENT_URI = ContactsContract.Data.CONTENT_URI;
    private static final String DATA_CONTACT_ID = ContactsContract.Data.CONTACT_ID;

    private static final String CONTACTS_ID = ContactsContract.Contacts._ID;
    private static final Uri CONTACTS_CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;

    private static final String STRUCTURED_POSTAL_CONTENT_ITEM_TYPE = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE;
    private static final String STRUCTURED_POSTAL_FORMATTED_ADDRESS = ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS;

    private static final int PICK_CONTACT_REQUEST = 9999;

//MINIMAPS END

    private String TAG = "BOSTON";

//MINIMAPS START
    private Button btnShowOnMap;
    private EditText edtLocation;
    private Button btnGetFromContacts;
//MINIMAPS END


//ANIMATION START
    private Button btnAnimate;
    private ImageView imgComic;
    private Animation bounceanim, wobbleranim, togetheranim, slideleftanim;
//ANIMATION END


//MISC INTENTS START
    private Button btnCallMom;
    private Button btnActuallyCallMom;
    private Button btnSMSMom;
    private Button btnSMSManagerMom;
    private EditText edtMomsPhoneNo;
//MISC INTENTS END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Setting up VIew References.
        edtLocation = (EditText) findViewById(R.id.edtLocation);
        btnShowOnMap = (Button) findViewById(R.id.btnShowOnMap);
        btnGetFromContacts = (Button) findViewById(R.id.btnGetFromContacts);
        btnAnimate = (Button) findViewById(R.id.btnAnimate);
        imgComic = (ImageView) findViewById(R.id.imgComic);
        bounceanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        wobbleranim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wobbler);   //ref: http://stackoverflow.com/questions/9448732/shaking-wobble-view-animation-in-android
        togetheranim= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.together);

        slideleftanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideleft);
        btnCallMom = (Button) findViewById(R.id.btnCallMom);
        btnActuallyCallMom = (Button) findViewById(R.id.btnActuallyCallMom);
        btnSMSMom = (Button) findViewById(R.id.btnSMSMom);
        btnSMSManagerMom = (Button) findViewById(R.id.btnSMSManagerMom);
        edtMomsPhoneNo = (EditText) findViewById(R.id.edtMomsPhoneNo);

    // Called when user clicks the Show Map button
    // Opening Map Address specified in EditText.
        btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"Vanilla flavored" Implicit Intent to send a location to an App, typically some mapping app.
                //But it really depends on who's listening.  It doesn't have to be a map that consumes
                //our intent!

                try {
                    String loc = edtLocation.getText().toString();
                    loc = loc.replace(' ', '+');  //the geoLocationIntent URI doesn't like spaces...
                    Intent geoLocationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + loc));  //A URI is just a consistent way of identifying a resource.
                                                                                                               // A URL is an example of a URI!
                    startActivity(geoLocationIntent);  //Broadcasting our implicit intent. Let's see who answers the Bat Signal.
                                                       // Wait, that was it?
                                                       // Yep, Android Framework makes it quick and easy to open other Apps.
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });


//MINIMAPS START
// Let's work with a Content Provider on our device.
// There are many Content Providers, we will work with Contacts.
// Routines to Open MAP Location from Contact's Address
        btnGetFromContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //New intent to open Contacts.
                    // Create Intent object for (parm 1) picking data from (parm 2) Contacts database
                    Intent geoLocationFromAddressIntent = new Intent(Intent.ACTION_PICK, CONTACTS_CONTENT_URI);  //good ref, ACTION_PICK Options- http://sudhanshuvinodgupta.blogspot.com/2012/07/using-intentactionpick.html
                    //Start the Activity to open contacts, and return result.  What result?  Success, Failure, something else,
                    // Use intent to start Contacts application
                    // Variable PICK_CONTACT_REQUEST identifies this operation, it's like dye.
                    startActivityForResult(geoLocationFromAddressIntent, PICK_CONTACT_REQUEST);  //the 9999 represents an expected result code, best to use a constant.
                } catch (Exception e) {
                    Log.e(TAG, e.toString());  //errors are in red.
                }
            }
        });


//MISC INTENTS -- USING TELEPHONY DEVICES START
        btnCallMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = edtMomsPhoneNo.getText().toString();
//                Intent phoneCallMom = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNo));
                Intent phoneCallMom = new Intent(Intent.ACTION_DIAL);  //or with two lines.
                phoneCallMom.setData(Uri.parse("tel:"+phoneNo));   //REALLY SHOULD NOT HARD CODE PHONE #
                startActivity(phoneCallMom);

            }
        });

        btnActuallyCallMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = edtMomsPhoneNo.getText().toString();
                Intent phoneCallMom = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNo));   //illustration purposes only, don't do this.  Also requires additional permissions.
                startActivity(phoneCallMom);
            }
        });
//MISC INTENTS -- USING TELEPHONY DEVICES END

//MISC INTENTS -- SENDING SMS MESSAGES 2-WAYS START
 //       REF: http://www.mkyong.com/android/how-to-send-sms-message-in-android/

// First Approach for sending SMS's
// This approach to sending SMS is considered "manual" create the intent, and start the activity.
        btnSMSMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String phoneNo = edtMomsPhoneNo.getText().toString();
                    String message = "Hi Mom";

                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("address", phoneNo);
                    sendIntent.putExtra("sms_body", message);
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

// Second Approach for sending SMS's
// The android framework has an SmsManager Object.  Just get the reference and use it as per the documentation.
// This approach to sending SMS is more automatic.

        btnSMSManagerMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String phoneNo = edtMomsPhoneNo.getText().toString();
                    String message = "Hi Mom";

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
//MISC INTENTS -- SENDING SMS MESSAGES TWO WAYS END

//ANIMATIONS START
        btnAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//LET'S TRY EACH OF THESE APPROACHES, AD HOC.
//
//                imgComic.animate().setDuration(3000);
//                imgComic.animate().alpha(0.25f);
//                imgComic.animate().rotation(5);
 //               imgComic.animate().rotation(-360);
//
//                imgComic.animate().alpha(0.1f);

//SIMPLE TILT
//                    imgComic.animate().setDuration(500);
//                    imgComic.animate().rotation(10 * sgn);


                //simple wobble (q&d to illustrate what not to do.)
                //this won't work, because there is no delay in between calls,
                //new calls overwrite older calls, before the older calls can finish.
                //a handler can insert a delay, but this would still not be a good way
                //to code animation in Android.
                //use an animator instead.  Let's try it.
//                int sgn=1;
//                for(int i=0;i<10;i++)
//                {
//                    imgComic.animate().setDuration(500);
//                    imgComic.animate().rotation(10 * sgn);
//                    if (i%2==0)
//                        sgn=1;
//                    else
//                        sgn = -1;
//                }


                imgComic.startAnimation(wobbleranim);  //this is a custom anim.
 //               imgComic.startAnimation(bounceanim);  //this is a custom anim.
   //             imgComic.startAnimation(togetheranim);  //this is a custom anim.

    //            imgComic.startAnimation(slideleftanim);

//these are animations that come with android studio.
//          imgComic.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_out_right));
//          imgComic.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));

            }
        });

}
//ANIMATIONS END


//StartActivity for Result (For Next Lecture).
//MINIMAPS START CALLBACK for StartActivityForResult -- NOTE THIS HANDLER IS OUTSIDE OF THE ONCREATE CONSTRUCTOR.
    //PART 2: Query your contacts to get the first available address associated with that contact.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Ensure that this call is the result of a successful PICK_CONTACT_REQUEST request
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_CONTACT_REQUEST) {  //if true, we know we got some valid data back, and that this corresponds to the right activity.
            ContentResolver cr = getContentResolver();  //ContentResolver, used to resolve different types of Content.  In this case Contacts... just another android manager, like, Fragment Manager, Sensor Manager, etc.
            Cursor cursor = cr.query(data.getData(), null, null, null, null);  //the cursor is also an iterator into the "table" of contacts.
                                                                               //we don't need to iterate over the records, because we picked a specific contact.
            if (cursor != null && cursor.moveToFirst()) {
                String id = cursor.getString(cursor.getColumnIndex(CONTACTS_ID));             //Formulating an SQL to query device contacts.
                String whereClause = DATA_CONTACT_ID + " = ? AND " + DATA_MIMETYPE + " = ?";  //It is common to pass parms this way in other DB's
                String[] parms = new String[] { id, STRUCTURED_POSTAL_CONTENT_ITEM_TYPE };    //Parms get passed in order.  Not really maintainable, but good enough.
                Cursor addrCur = null;                                                        //ParmByName() is another common way, and more maintainable.
                try{
                    //We need permission.  This is close, but not right.  Asking now is too late.  Why?  A: ___Because the request is ansynchronous.  You won't have permission right away.  Next time tho.
                    getPermissionToReadUserContacts();  //Needed for Android 6.0 and higher, in addition to Manifest settings.

//while(true){
//    if (flag = checkCallingOrSelfPermission(break))
//
//
//}
                    addrCur = cr.query(DATA_CONTENT_URI, null, whereClause, parms, null);}    //This cursor contains Address(es).
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ADDRESS LOCATION failed, please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                //Treat addrCurr like a dataset, could contain multiple records or none.
                if (addrCur != null && addrCur.moveToFirst()) {//checking that we got a result, AND moving to first of possible several addresses.

                   while (!addrCur.isLast()){
                       addrCur.moveToFirst();
                       //do somestuff
                       addrCur.moveToNext();


                   }
                    String formattedAddress = addrCur.getString(addrCur.getColumnIndex(STRUCTURED_POSTAL_FORMATTED_ADDRESS));

                    if (formattedAddress != null) {  //don't want to parse or open a non-existant address, so check for null first.

                        // Process text for network transmission
                        formattedAddress = formattedAddress.replace(' ', '+');  //google maps doesn't want spaces, it wants +'s.

                        // Create Intent object for starting Google Maps application
                        Intent geoIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + formattedAddress));
                        // Use the Intent to start Google Maps (or some other similar) application using Activity.startActivity()
                                      startActivity(geoIntent);
                    }

                }
                if (null != addrCur)
                    addrCur.close();  //Clean up table cursors
            }
            if (null != cursor)
                cursor.close();  //Clean up table cursors (just like closing a file)
        }
    }
//MINIMAPS END


    public void onTestClick(View v) {

        //Todo
    }


// For Users of Android 6.0 and up, simply adding
//    <uses-permission android:name="android.permission.READ_CONTACTS"/>
// to the Manifest is not enough.
// The user must grant you permission at RunTime, at least once.
// As a Developer you wouldn't request this until you need it.
//Ref: https://stackoverflow.com/questions/5233543/java-lang-securityexception-trying-to-read-from-android-contacts-uri

// Identifier for the permission request, like a dye that we can track later.
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    public void getPermissionToReadUserContacts() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
//            if (shouldShowRequestPermissionRationale(
//                    Manifest.permission.READ_CONTACTS)) {
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//                alertDialogBuilder.setTitle("Permission Request");
//                alertDialogBuilder.setMessage("This APP requires access to your Contacts.  Please acknowledged this on the next screen.");
//                alertDialogBuilder.setPositiveButton("OK", null);
//                alertDialogBuilder.create().show();
//
//                // Display a popup message or equivalent.  Explain to the user why we need to read the contacts
//                // before actually requesting the permission and showing the default UI
//            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_PERMISSIONS_REQUEST);
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}