package com.example.yara;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class ResultFragment extends Fragment {

    public interface OnResultListener {
        void onCancel();
    }

    private OnResultListener listener;
    private TextView tvResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        tvResult = view.findViewById(R.id.tvResultText);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(v -> {
            if (listener != null) listener.onCancel();
        });
        return view;
    }

    public void updateResult(String text) {
        if (tvResult != null) {
            tvResult.setText(text);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnResultListener) {
            listener = (OnResultListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnResultListener");
        }
    }
}