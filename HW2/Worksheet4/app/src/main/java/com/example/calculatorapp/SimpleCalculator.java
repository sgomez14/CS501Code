package com.example.calculatorapp;

public class SimpleCalculator {

    private float operand1;
    private float operand2;
    private String operator;
    private boolean operatorSet;
    private boolean calcDone;
    private float result;

    // com.example.calculatorapp.SimpleCalculator Constructor
    public SimpleCalculator(){

        // setting default values for class attributes
        this.operand1 = 0;
        this.operand2 = 0;
        this.operator = "";
        this.operatorSet = false;
        this.calcDone = false;
        this.result = 0;
    }

    public float getResult(){
        return result;
    }

    public String getOperator(){
        return operator;
    }

    public void setOperand1(float operand1) {
        this.operand1 = operand1;
    }

    public void setOperand2(float operand2) {
        this.operand2 = operand2;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setCalcDone(boolean calcDone){
        this.calcDone = calcDone;
    }

    public boolean isOperatorSet(){
        return operatorSet;
    }

    public boolean isCalcDone(){
        return calcDone;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public void resetCalculator(){
        this.operand1 = 0;
        this.operand2 = 0;
        this.operator = "";
        this.operatorSet = false;
        this.result = 0;
    }

    public float calculate(){
        switch (operator){
            case "+":
                result = operand1 + operand2;
                break;

            case "-":
                result = operand1 - operand2;
                break;

            case "*":
                result = operand1 * operand2;
                break;

            case "/":
                result = operand1 / operand2;
                break;

            case "sqrt":
                result = (float) Math.sqrt(operand1);
        }

        calcDone = true;
        return result;
    }


}
