package com.example.yao_hw2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Exception;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private Button btnSub;
    private Button btnMul;
    private Button btnDiv;
    private Button btnMod;
    private EditText edtOp1;
    private EditText edtOp2;
    private TextView tvAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtOp1 = (EditText) findViewById(R.id.edtOp1);
        edtOp2 = (EditText) findViewById(R.id.edtOp2);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnMod = (Button) findViewById(R.id.btnMod);
        tvAnswer = (TextView) findViewById(R.id.tvAnswer);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                try {
                    Double result;
                    result = Double.parseDouble(edtOp1.getText().toString())
                            + Double.parseDouble(edtOp2.getText().toString());
                    tvAnswer.setText(result.toString());
                } catch(Exception e) {
                    tvAnswer.setText("EXCEPTION CAUGHT " + e.getMessage());
                }

            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                try{
                    Double result;
                    result = Double.parseDouble(edtOp1.getText().toString())
                            - Double.parseDouble(edtOp2.getText().toString());
                    tvAnswer.setText(result.toString());
                }catch(Exception e){
                    tvAnswer.setText("EXCEPTION CAUGHT " + e.getMessage());
                }
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                try{
                    Double result;
                    result = Double.parseDouble(edtOp1.getText().toString())
                            * Double.parseDouble(edtOp2.getText().toString());
                    tvAnswer.setText(result.toString());
                }
                catch(Exception e){
                    tvAnswer.setText("EXCEPTION CAUGHT " + e.getMessage());
                }
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                try{
                    Double result;
                    if(Double.parseDouble(edtOp2.getText().toString()) == 0){
                        tvAnswer.setText("CANNOT DIVIDE BY ZERO!");
                        return;
                    }
                    result = Double.parseDouble(edtOp1.getText().toString())
                            / Double.parseDouble(edtOp2.getText().toString());
                    tvAnswer.setText(result.toString());
                }catch(Exception e){
                    tvAnswer.setText("EXCEPTION CAUGHT " + e.getMessage());
                }
            }
        });
        btnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                try{
                    Double result;
                    result = Double.parseDouble(edtOp1.getText().toString())
                            % Double.parseDouble(edtOp2.getText().toString());
                    tvAnswer.setText(result.toString());
                }catch(Exception e){
                    tvAnswer.setText("EXCEPTION CAUGHT " + e.getMessage());
                }
            }
        });
    }
}