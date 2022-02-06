public class SimpleCalculator {

    private float operand1;
    private float operand2;
    private String operator;
    private float result;

    public SimpleCalculator(){
        this.operand1 = 0;
        this.operand2 = 0;
    }

    public float getResult(){
        return result;
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

    public void setResult(float result) {
        this.result = result;
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

            case "\u00F7":
                result = operand1 / operand2;
                break;
        }

        return result;
    }
}
