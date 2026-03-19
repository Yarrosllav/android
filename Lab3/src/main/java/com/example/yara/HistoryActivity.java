package com.example.yara;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private TextView tvHistoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DBHelper(this);
        tvHistoryData = findViewById(R.id.tvHistoryData);
        tvHistoryData.setMovementMethod(new ScrollingMovementMethod());

        Button btnClearDB = findViewById(R.id.btnClearDB);

        loadData();

        btnClearDB.setOnClickListener(v -> {
            dbHelper.clearHistory();
            Toast.makeText(this, "Історію очищено", Toast.LENGTH_SHORT).show();
            loadData();
        });
    }

    private void loadData() {
        ArrayList<String> historyList = dbHelper.getAllHistory();

        if (historyList.isEmpty()) {
            tvHistoryData.setText("Сховище пусте");
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                builder.append(i + 1).append(". ").append(historyList.get(i)).append("\n\n");
            }
            tvHistoryData.setText(builder.toString());
        }
    }
}