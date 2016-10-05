package com.edsgerlin.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean previousCharacterIsDigit = false;
    TextView displayTextView;
    final StringBuilder numberStringBuilder = new StringBuilder();
    final StringBuilder expressionStringBuilder = new StringBuilder();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayTextView = (TextView) findViewById(R.id.text_display);
    }

    public void onButtonClick(final View view) {
        if (view instanceof Button) {
            final Button button = (Button) view;
            final int buttonId = button.getId();
            switch (buttonId) {
                case R.id.button_number_0:
                    handleDigitInput('0');
                    break;
                case R.id.button_number_1:
                    handleDigitInput('1');
                    break;
                case R.id.button_number_2:
                    handleDigitInput('2');
                    break;
                case R.id.button_number_3:
                    handleDigitInput('3');
                    break;
                case R.id.button_number_4:
                    handleDigitInput('4');
                    break;
                case R.id.button_number_5:
                    handleDigitInput('5');
                    break;
                case R.id.button_number_6:
                    handleDigitInput('6');
                    break;
                case R.id.button_number_7:
                    handleDigitInput('7');
                    break;
                case R.id.button_number_8:
                    handleDigitInput('8');
                    break;
                case R.id.button_number_9:
                    handleDigitInput('9');
                    break;
                case R.id.button_decimal_mark:
                    handleDigitInput('.');
                    break;
                case R.id.button_division_sign:
                    handleOperatorInput('/');
                    break;
                case R.id.button_minus_sign:
                    handleOperatorInput('-');
                    break;
                case R.id.button_multiplication_sign:
                    handleOperatorInput('*');
                    break;
                case R.id.button_plus_sign:
                    handleOperatorInput('+');
                    break;
                case R.id.button_equals_sign:
                    evaluation();
                    break;
                default:
                    // Ignore it.
                    break;
            }
        }
    }

    private void handleDigitInput(final char digit) {
        if (!previousCharacterIsDigit) {
            numberStringBuilder.setLength(0);
        }
        numberStringBuilder.append(digit);
        displayTextView.setText(numberStringBuilder);
        previousCharacterIsDigit = true;
    }

    private void handleOperatorInput(final char operator) {
        expressionStringBuilder.append(numberStringBuilder);
        expressionStringBuilder.append(operator);
        previousCharacterIsDigit = false;
    }

    private void evaluation() {
        expressionStringBuilder.append(numberStringBuilder);
        final ExpressionParser parser = new ExpressionParser(expressionStringBuilder.toString());
        numberStringBuilder.setLength(0);
        numberStringBuilder.append(parser.parse().eval());
        displayTextView.setText(numberStringBuilder);
        previousCharacterIsDigit = true;
        expressionStringBuilder.setLength(0);
    }

}

