package com.example.w3_p3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // declare GUI elements
    private SeekBar skbCelsius;
    private SeekBar skbFahrenheit;
    private TextView tvCelsius;
    private TextView tvResultC;
    private TextView tvFahrenheit;
    private TextView tvResultF;
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skbCelsius = (SeekBar) findViewById(R.id.skbCelsius);
        skbFahrenheit = (SeekBar) findViewById(R.id.skbFahrenheit);

        tvCelsius = (TextView) findViewById(R.id.tvCelsius);
        tvResultC = (TextView) findViewById(R.id.tvResultC);
        tvFahrenheit = (TextView) findViewById(R.id.tvFahrenheit);
        tvResultF = (TextView) findViewById(R.id.tvResultF);
        tvMsg = (TextView) findViewById(R.id.tvMsg);

        skbCelsius.setProgress(0);
        skbFahrenheit.setProgress(32);

        skbCelsius.incrementProgressBy(1);
        skbFahrenheit.incrementProgressBy(1);

        skbCelsius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b)
            {
                tvResultC.setText(String.valueOf(progress));

                Double conversion = convertTemp((double) progress, "c");

                skbFahrenheit.setProgress(conversion.intValue());

                String msg;
                if (progress <= 20)
                {
                    msg = getResources().getString(R.string.msgWarmer);
                }
                else
                {
                    msg = getResources().getString(R.string.msgColder);
                }
                tvMsg.setText(msg);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                int progress = seekBar.getProgress();

                String msg;
                if (progress <= 20)
                {
                    msg = getResources().getString(R.string.msgWarmer);
                }
                else
                {
                    msg = getResources().getString(R.string.msgColder);
                }
                tvMsg.setText(msg);
            }
        });

        skbFahrenheit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            final double MIN_VAL = 32.0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b)
            {
                if (progress <= 32)
                {
                    progress = 32;
                }

                tvResultF.setText(String.valueOf(progress));

                Double conversion = convertTemp((double) progress, "f");

                skbCelsius.setProgress(conversion.intValue());

                String msg;
                if (progress <= 68)
                {
                    msg = getResources().getString(R.string.msgWarmer);
                }
                else
                {
                    msg = getResources().getString(R.string.msgColder);
                }
                tvMsg.setText(msg);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                // this code snaps the seekbar back to 32
                int progress = seekBar.getProgress();

                if (progress <= 32)
                {
                    progress = 32;
                    skbFahrenheit.setProgress(progress);
                }


                String msg;
                if (progress <= 68)
                {
                    msg = getResources().getString(R.string.msgWarmer);
                }
                else
                {
                    msg = getResources().getString(R.string.msgColder);
                }
                tvMsg.setText(msg);

            }
        });
    }

    private Double convertTemp(Double temp, String unit)
    {
        Double result = Double.MAX_VALUE;

        if (unit.toLowerCase().equals("c"))
        {
            // convert to Fahrenheit
            result = (temp * 1.8) + 32;

        }
        else if (unit.toLowerCase().equals("f"))
        {
            // convert to Celsius
            result = (temp - 32) / 1.8;
        }

        return result;
    }
}