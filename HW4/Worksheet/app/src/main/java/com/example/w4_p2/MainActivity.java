package com.example.w4_p2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

///////  Set of imports for Gesture Detection ////////
import android.view.MotionEvent;
import android.view.GestureDetector;
//////////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener {

    // declare Views
    private TextView tvEuroAmount;
    private TextView tvYenAmount;
    private TextView tvCADAmount;
    private EditText edtDollar;

    // declare gesture detector
    private GestureDetector GD;    //must instantiate the gesture detector

    // define step size for gestures
    final private Double FLING_AMT = 1.0;
    final private Double SCROLL_AMT = 0.1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GD = new GestureDetector(this, this);   //Context, Listener as per Constructor Doc.

        // instantiate Views
        tvEuroAmount = (TextView) findViewById(R.id.tvEuroAmount);
        tvYenAmount  = (TextView) findViewById(R.id.tvYenAmount);
        tvCADAmount  = (TextView) findViewById(R.id.tvCADAmount);
        edtDollar    = (EditText) findViewById(R.id.edtDollar);




        edtDollar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String amount = edtDollar.getText().toString();
                if (!amount.equals("")){
//                    Double [] conversions = convertMoney(amount);
//                    tvEuroAmount.setText(Double.toString(conversions[0]));
//                    tvYenAmount.setText(Double.toString(conversions[1]));
//                    tvCADAmount.setText(Double.toString(conversions[2]));
                    convertMoney(amount);

                }
                else{
                    edtDollar.setText("0");
                    convertMoney("0");
                }

            }
        });

    }

    private void convertMoney(String dollars){
        final double euro_coefficient = .88;
        final double yen_coefficient = 114.74;
        final double cad_coefficient = 1.28;

        double usd_amount = Double.parseDouble(dollars);
        double euro_amount = Math.round((euro_coefficient * usd_amount)*100.0)/100.0;
        double yen_amount = Math.round((yen_coefficient * usd_amount)*100.0)/100.0;
        double cad_amount = Math.round((cad_coefficient * usd_amount)*100.0)/100.0;

//        edtDollar.setText(dollars);
        tvEuroAmount.setText(Double.toString(euro_amount));
        tvYenAmount.setText(Double.toString(yen_amount));
        tvCADAmount.setText(Double.toString(cad_amount));

//        Double [] conversions = {euro_amount, yen_amount, cad_amount};
//        return conversions;

    }

    //////////////////////////////////////////////////////////////////////////
    //very important step, otherwise we won't be able to capture our touches//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);               //Our GD will not automatically receive Android Framework Touch notifications.
        // Insert this line to consume the touch event locally by our GD,
        // IF YOU DON'T insert this before the return, our GD will not receive the event, and therefore won't do anything.
        return super.onTouchEvent(event);          // Do this last, why?
    }
    //////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent startEvent, MotionEvent endEvent, float velocityX, float velocityY) {
        boolean result = false;
        final double SCROLL_THRESHOLD = 50;
        final double SCROLL_VELOCITY_THRESHOLD = 50;
        double deltaY = endEvent.getY() - startEvent.getY();
        double deltaX = endEvent.getX() - startEvent.getX();

        // which was greater? movement across Y or X?
        if (Math.abs(deltaX) > Math.abs(deltaY)){
            // right or left scroll
            if (Math.abs(deltaX) < SCROLL_THRESHOLD && Math.abs(velocityX) < SCROLL_VELOCITY_THRESHOLD){
                if (deltaX > 0){
                    onScrollRight();
                    result = true;
                }
                else{
                    onScrollLeft();
                    result = true;
                }
            }
        }
        else{
            // up or down scroll
            if (Math.abs(deltaY) < SCROLL_THRESHOLD && Math.abs(velocityY) < SCROLL_VELOCITY_THRESHOLD){
                if (deltaY > 0){
                    onScrollDown();
                    result = true;
                }
                else{
                    onScrollUp();
                    result = true;
                }
            }
        }

        return result;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent startEvent, MotionEvent endEvent, float velocityX, float velocityY) {
        boolean result = false;
        final double SWIPE_THRESHOLD = 500;
        final double SWIPE_VELOCITY_THRESHOLD = 8000;
        double deltaY = endEvent.getY() - startEvent.getY();
        double deltaX = endEvent.getX() - startEvent.getX();

        // which was greater? movement across Y or X?
        if (Math.abs(deltaX) > Math.abs(deltaY)){
            // right or left swipe
            if (Math.abs(deltaX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                if (deltaX > 0){
                    onSwipeRight();
                    result = true;
                }
                else{
                    onSwipeLeft();
                    result = true;
                }
            }
        }
        else{
            // up or down swipe
            if (Math.abs(deltaY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD){
                if (deltaY > 0){
                    onSwipeDown();
                    result = true;
                }
                else{
                    onSwipeUp();
                    result = true;
                }
            }
        }

        return result;
    }

    private void onSwipeDown() {
//        Toast.makeText(this, "Swipe Down", Toast.LENGTH_SHORT).show();
        String amount = edtDollar.getText().toString();

        if (!amount.equals("")){
            Double usd = Double.parseDouble(amount);
            if (usd > 0){
                usd -= FLING_AMT;
                usd = Math.round((usd)*100.0)/100.0;
                edtDollar.setText(Double.toString(usd));
                convertMoney(Double.toString(usd));
            }
            else {
                edtDollar.setText("0.0");
                convertMoney("0");
            }
        }


    }

    private void onSwipeUp() {
//        Toast.makeText(this, "Swipe Up", Toast.LENGTH_SHORT).show();
        String amount = edtDollar.getText().toString();

        if (!amount.equals("")){
            Double usd = Double.parseDouble(amount);
            if (usd >= 0){
                usd += FLING_AMT;
                usd = Math.round((usd)*100.0)/100.0;
                edtDollar.setText(Double.toString(usd));
                convertMoney(Double.toString(usd));
            }
            else {
                edtDollar.setText("0.0");
                convertMoney("0");
            }
        }
        else {
            edtDollar.setText("1");
            convertMoney("1");
        }
    }

    private void onSwipeLeft() {
        Toast.makeText(this, "Swipe Left", Toast.LENGTH_SHORT).show();
    }

    private void onSwipeRight() {
        Toast.makeText(this, "Swipe Right", Toast.LENGTH_SHORT).show();
    }

    private void onScrollUp() {
        String amount = edtDollar.getText().toString();

        if (!amount.equals("")){
            Double usd = Double.parseDouble(amount);
            if (usd >= 0){
                usd += SCROLL_AMT;
                usd = Math.round((usd)*100.0)/100.0;
                edtDollar.setText(Double.toString(usd));
                convertMoney(Double.toString(usd));
            }
            else {
                edtDollar.setText("0.0");
                convertMoney("0");
            }
        }
        else {
            edtDollar.setText(".1");
            convertMoney(".1");
        }

    }

    private void onScrollDown() {
        String amount = edtDollar.getText().toString();

        if (!amount.equals("")){
            Double usd = Double.parseDouble(amount);
            if (usd > 0){
                usd -= SCROLL_AMT;
                usd = Math.round((usd)*100.0)/100.0;
                edtDollar.setText(Double.toString(usd));
                convertMoney(Double.toString(usd));
            }
            else {
                edtDollar.setText("0.0");
                convertMoney("0");
            }
        }
    }

    private void onScrollLeft() {
    }

    private void onScrollRight() {
    }


}
