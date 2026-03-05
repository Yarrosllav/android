package com.example.yara;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment {

    public interface OnInputListener {
        void sendResult(String result);
    }

    private OnInputListener listener;
    private EditText etInput;
    private RadioGroup radioGroup;
    private CheckBox cbArea, cbPerimeter;
    private RadioButton rbCircle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        etInput = view.findViewById(R.id.etInput);
        radioGroup = view.findViewById(R.id.radioGroupShapes);
        cbArea = view.findViewById(R.id.cbArea);
        cbPerimeter = view.findViewById(R.id.cbPerimeter);
        rbCircle = view.findViewById(R.id.rbCircle);
        Button btnOk = view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(v -> calculate());

        return view;
    }

    public void clearForm() {
        etInput.setText("");
        radioGroup.clearCheck();
        cbArea.setChecked(false);
        cbPerimeter.setChecked(false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnInputListener) {
            listener = (OnInputListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnInputListener");
        }
    }

    private void calculate() {
        String inputStr = etInput.getText().toString();

        if (inputStr.isEmpty()) {
            Toast.makeText(getContext(), "Введіть число!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Оберіть фігуру!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!cbArea.isChecked() && !cbPerimeter.isChecked()) {
            Toast.makeText(getContext(), "Оберіть, що рахувати!", Toast.LENGTH_SHORT).show();
            return;
        }

        double val = Double.parseDouble(inputStr);
        StringBuilder res = new StringBuilder();
        boolean isCircle = (radioGroup.getCheckedRadioButtonId() == R.id.rbCircle);

        if (isCircle) {
            res.append("Коло (R=").append(val).append(")\n");
            if (cbArea.isChecked()) res.append("S = ").append(String.format("%.2f", Math.PI * val * val)).append("\n");
            if (cbPerimeter.isChecked()) res.append("P = ").append(String.format("%.2f", 2 * Math.PI * val)).append("\n");
        } else {
            res.append("Квадрат (A=").append(val).append(")\n");
            if (cbArea.isChecked()) res.append("S = ").append(val * val).append("\n");
            if (cbPerimeter.isChecked()) res.append("P = ").append(4 * val).append("\n");
        }

        listener.sendResult(res.toString());
    }
}