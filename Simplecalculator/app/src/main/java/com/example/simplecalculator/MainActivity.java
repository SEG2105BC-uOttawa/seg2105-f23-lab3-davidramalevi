package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private TextView resultTextView;
    private StringBuilder currentExpression;
    private String operator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        resultTextView = findViewById(R.id.resultTextView);
        currentExpression = new StringBuilder();

        // Set click listeners for digit buttons (0-9)
        for (int i = 0; i <= 9; i++) {
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            setDigitClickListener(button, i);
        }

        // Set click listeners for operator buttons (+, -, *, /)
        setOperatorClickListener(R.id.buttonPlus, "+");
        setOperatorClickListener(R.id.buttonMinus, "-");
        setOperatorClickListener(R.id.buttonMultiply, "*");
        setOperatorClickListener(R.id.buttonDivide, "/");

        // Set click listener for the equals button (=)
        Button equalsButton = findViewById(R.id.buttonEquals);
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double result = evaluateExpression();
                    resultTextView.setText(String.valueOf(result));
                } catch (Exception e) {
                    resultTextView.setText("Error");
                }
            }
        });

        // Set click listener for the clear button (C)
        Button clearButton = findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExpression.setLength(0);
                resultTextView.setText("0");
                operator = null;
            }
        });
    }

    private void setDigitClickListener(Button button, final int digit) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression(String.valueOf(digit));
            }
        });
    }

    private void setOperatorClickListener(int buttonId, final String newOperator) {
        Button operatorButton = findViewById(buttonId);
        operatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentExpression.length() > 0 && operator != null) {
                    try {
                        double result = evaluateExpression();
                        currentExpression.setLength(0);
                        currentExpression.append(result);
                        resultTextView.setText(String.valueOf(result));
                    } catch (Exception e) {
                        resultTextView.setText("Error");
                    }
                }
                operator = newOperator;
                currentExpression.append(" " + operator + " ");
            }
        });
    }

    private void appendToExpression(String value) {
        currentExpression.append(value);
        resultTextView.setText(currentExpression.toString());
    }

    private double evaluateExpression() {
        String[] tokens = currentExpression.toString().split(" ");
        double operand1 = Double.parseDouble(tokens[0]);
        double operand2 = Double.parseDouble(tokens[2]);

        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
            default:
                throw new IllegalArgumentException("Invalid operator");
        }

    }
}