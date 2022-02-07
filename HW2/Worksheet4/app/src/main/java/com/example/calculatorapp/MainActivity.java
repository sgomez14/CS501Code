package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Declare SimpleCalculator object
    private SimpleCalculator simpleCalc;

    // Declare variable for storing displayed number
    private String numDisplay;

    // Declare EditText object for displaying numbers
    private EditText editTextNumberDisplay;

    // Declare Button object for clear
    private Button btnClear;

    // Declare Button objects for numbers
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;

    // Declare Button objects for operators
    private Button btnAdd;
    private Button btnSub;
    private Button btnMul;
    private Button btnDiv;
    private Button btnSqrt;
    private Button btnEquals;
    private Button btnDeciPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate SimpleCalculator object
        simpleCalc = new SimpleCalculator();

        // Set display string variable to empty
        numDisplay = "";

        // Define EditText object
        editTextNumberDisplay = (EditText) findViewById(R.id.editTextNumberDisplay);

        // Define all the Button objects
        btnClear = (Button) findViewById(R.id.btnClear);

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        btnAdd       =  (Button) findViewById(R.id.btnAdd);
        btnSub       =  (Button) findViewById(R.id.btnSub);
        btnMul       =  (Button) findViewById(R.id.btnMul);
        btnDiv       =  (Button) findViewById(R.id.btnDiv);
        btnSqrt      =  (Button) findViewById(R.id.btnSqrt);
        btnEquals    =  (Button) findViewById(R.id.btnEquals);
        btnDeciPoint =  (Button) findViewById(R.id.btnDeciPoint);

        TextChangeHandler tch = new TextChangeHandler();

        // Listener for changes in the display
        editTextNumberDisplay.addTextChangedListener(tch);

        // Event handlers for the buttons
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reset calculator
                clearClickSequence();
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // would like to add some parsing logic for removing leading zeros

                // Check if computation recently completed
                didCalcRecentlyComplete();

                numDisplay += 0;
                editTextNumberDisplay.setText(numDisplay);


            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if computation recently completed
                didCalcRecentlyComplete();

                // concatenate numbers entered
                numDisplay += 1;
                editTextNumberDisplay.setText(numDisplay);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if computation recently completed
                didCalcRecentlyComplete();

                // concatenate numbers entered
                numDisplay += 2;
                editTextNumberDisplay.setText(numDisplay);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if computation recently completed
                didCalcRecentlyComplete();

                // concatenate numbers entered
                numDisplay += 3;
                editTextNumberDisplay.setText(numDisplay);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if computation recently completed
                didCalcRecentlyComplete();

                // concatenate numbers entered
                numDisplay += 4;
                editTextNumberDisplay.setText(numDisplay);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if computation recently completed
                didCalcRecentlyComplete();

                // concatenate numbers entered
                numDisplay += 5;
                editTextNumberDisplay.setText(numDisplay);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if computation recently completed
                didCalcRecentlyComplete();

                // concatenate numbers entered
                numDisplay += 6;
                editTextNumberDisplay.setText(numDisplay);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if computation recently completed
                didCalcRecentlyComplete();

                // concatenate numbers entered
                numDisplay += 7;
                editTextNumberDisplay.setText(numDisplay);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if computation recently completed
                didCalcRecentlyComplete();

                // concatenate numbers entered
                numDisplay += 8;
                editTextNumberDisplay.setText(numDisplay);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if computation recently completed
                didCalcRecentlyComplete();

                // concatenate numbers entered
                numDisplay += 9;
                editTextNumberDisplay.setText(numDisplay);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Process operator
                operatorClickSequence("+");
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Process operator
                operatorClickSequence("-");
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Process operator
                operatorClickSequence("*");
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Process operator
                operatorClickSequence("/");
            }
        });

        btnSqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Process operator
                operatorClickSequence("sqrt");
            }
        });

        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Process equals click
                equalsClickSequence();
            }
        });

        btnDeciPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentDisplay = editTextNumberDisplay.getText().toString();

                if (simpleCalc.isOperatorSet()){
                    numDisplay = ".";

                    // Display new decimal number for operand2
                    editTextNumberDisplay.setText(numDisplay);
                }

                // Check if current display number does not have a decimal point
                else if (!currentDisplay.contains(".")){
                    numDisplay = currentDisplay + ".";

                    // Display number with added decimal point
                    editTextNumberDisplay.setText(numDisplay);
                }
            }
        });

    }

    private class TextChangeHandler implements TextWatcher {
        public void afterTextChanged (Editable e) {
            numDisplay = editTextNumberDisplay.getText().toString();
        }

        public void beforeTextChanged ( CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged ( CharSequence s, int before, int count, int after) {

        }
    }

    private void didCalcRecentlyComplete(){

        if (simpleCalc.isCalcDone()){
            // reset the numDisplay so that new number can be assigned to operand1
            numDisplay = "";

            // reset the calcDone attribute so that new calculation can take place
            simpleCalc.setCalcDone(false);

            // reset calculator object
            simpleCalc.resetCalculator();
        }

    }

    private void operatorClickSequence(String operator){
        // Parse operator
        switch (operator){
            case "+":
                simpleCalc.setOperator("+");
                break;

            case "-":
                simpleCalc.setOperator("-");
                break;

            case "*":
                simpleCalc.setOperator("*");
                break;

            case "/":
                simpleCalc.setOperator("/");
                break;

            case "sqrt":
                simpleCalc.setOperator("sqrt");
                break;

            default:
                // Invalid operator passed
                simpleCalc.resetCalculator();
        }

        // Clear status of previous calculation
        simpleCalc.setCalcDone(false);

        // Set operand1
        numDisplay = editTextNumberDisplay.getText().toString();
        simpleCalc.setOperand1(Float.parseFloat(numDisplay));

        // Clear current value of numDisplay so that operand2 can be set next
        numDisplay = "";

    }

    private void equalsClickSequence(){
        // Set operand2
        if (simpleCalc.getOperator() != "sqrt"){
            simpleCalc.setOperand2(Float.parseFloat(numDisplay));
        }

        try {
            String operator = simpleCalc.getOperator();

            // Check for DivideByZero
            if (operator.equals("/")){
                float operand2 = simpleCalc.getOperand2();

                // Set boolean expression to check for DivideByZero
                boolean divideByZero = (operand2 == 0);

                if (divideByZero){
                    editTextNumberDisplay.setText("CANNOT DIVIDE BY ZERO!");

                    return;
                }
            }

            // Check for negative roots
            if (operator.equals("sqrt") && simpleCalc.getOperand1() < 0)
            {
                editTextNumberDisplay.setText("NO NEGATIVE ROOTS!");

                return;
            }

            // do calculation
            simpleCalc.calculate();

            // get result from calculator object
            float result = simpleCalc.getResult();

            // display result
            editTextNumberDisplay.setText(Float.toString(result));

            // Reset calculator
            simpleCalc.resetCalculator();


        }
        catch (Exception e){
            editTextNumberDisplay.setText("EXCEPTION CAUGHT " + e.getMessage());
        }
    }

    private void clearClickSequence(){
        // Reset calculator
        simpleCalc.resetCalculator();
        simpleCalc.setCalcDone(false);
        numDisplay = "";
        editTextNumberDisplay.setText(numDisplay);
    }
}