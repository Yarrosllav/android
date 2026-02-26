package com.example.lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etInput;
    private RadioGroup radioGroupShapes;
    private RadioButton rbCircle, rbSquare;
    private CheckBox cbArea, cbPerimeter;
    private Button btnOk;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);
        radioGroupShapes = findViewById(R.id.radioGroupShapes);
        rbCircle = findViewById(R.id.rbCircle);
        rbSquare = findViewById(R.id.rbSquare);
        cbArea = findViewById(R.id.cbArea);
        cbPerimeter = findViewById(R.id.cbPerimeter);
        btnOk = findViewById(R.id.btnOk);
        tvResult = findViewById(R.id.tvResult);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });
    }

    private void calculateResult() {
        tvResult.setText("");

        String inputText = etInput.getText().toString();
        if (inputText.isEmpty()) {
            Toast.makeText(this, "Введіть необхідні дані", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedShapeId = radioGroupShapes.getCheckedRadioButtonId();
        if (selectedShapeId == -1) {
            Toast.makeText(this, "Оберіть фігуру", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cbArea.isChecked() && !cbPerimeter.isChecked()) {
            Toast.makeText(this, "Оберіть, що саме треба обчислити", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = 0;
        try {
            value = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Некоректне число", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder resultBuilder = new StringBuilder();

        // коло
        if (selectedShapeId == R.id.rbCircle) {
            resultBuilder.append("Фігура: Коло (Радіус = ").append(value).append(")\n");

            if (cbArea.isChecked()) {
                double area = Math.PI * value * value;
                resultBuilder.append(String.format("Площа: %.2f\n", area));
            }
            if (cbPerimeter.isChecked()) {
                double perimeter = 2 * Math.PI * value;
                resultBuilder.append(String.format("Периметр: %.2f\n", perimeter));
            }
        }
        // квадрат
        else if (selectedShapeId == R.id.rbSquare) {
            resultBuilder.append("Фігура: Квадрат (Сторона = ").append(value).append(")\n");

            if (cbArea.isChecked()) {
                double area = value * value;
                resultBuilder.append(String.format("Площа: %.2f\n", area));
            }
            if (cbPerimeter.isChecked()) {
                double perimeter = 4 * value;
                resultBuilder.append(String.format("Периметр: %.2f\n", perimeter));
            }
        }

        tvResult.setText(resultBuilder.toString());
    }
}