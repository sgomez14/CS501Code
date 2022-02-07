package com.example.calculatorapp;

public class SimpleCalculator {

    private float operand1;
    private float operand2;
    private String operator;
    private boolean operatorSet;
    private boolean calcDone;
    private boolean Operand1Set;
    private boolean Operand2Set;
    private float result;

    // com.example.calculatorapp.SimpleCalculator Constructor
    public SimpleCalculator(){

        // setting default values for class attributes
        this.operand1 = 0;
        this.operand2 = 0;
        this.operator = "";
        this.operatorSet = false;
        this.calcDone = false;
        this.Operand1Set = false;
        this.Operand2Set = false;
        this.result = 0;
    }

    public float getResult(){
        return result;
    }

    public String getOperator(){
        return operator;
    }

    public float getOperand1() {
        return operand1;
    }

    public float getOperand2() {
        return operand2;
    }

    public void setOperand1(float operand1) {
        this.operand1 = operand1;
        this.Operand1Set = true;
    }

    public void setOperand2(float operand2) {
        this.operand2 = operand2;
        this.Operand2Set = true;
    }

    public void setOperator(String operator) {
        operatorSet = true;
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

    public boolean isOperand1Set() {
        return Operand1Set;
    }

    public boolean isOperand2Set() {
        return Operand2Set;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public void resetCalculator(){
        this.operand1 = 0;
        this.operand2 = 0;
        this.operator = "";
        this.operatorSet = false;
        this.Operand1Set = false;
        this.Operand2Set = false;
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

            default:
                // invalid operator passed
                calcDone = false;
                return 0;
        }

        calcDone = true;
        return result;
    }


}
