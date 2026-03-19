package com.example.yara;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements
        InputFragment.OnInputListener,
        ResultFragment.OnResultListener {

    private InputFragment inputFragment;
    private ResultFragment resultFragment;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        inputFragment = new InputFragment();
        resultFragment = new ResultFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerInput, inputFragment)
                .commit();
    }

    @Override
    public void sendResult(String result) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (!resultFragment.isAdded()) {
            ft.replace(R.id.containerResult, resultFragment);
        } else {
            ft.show(resultFragment);
        }
        ft.commitNow();

        resultFragment.updateResult(result);

        boolean isInserted = dbHelper.insertResult(result);
        if (isInserted) {
            Toast.makeText(this, "Дані збережено в БД", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Помилка збереження", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancel() {
        if (inputFragment != null) {
            inputFragment.clearForm();
        }

        getSupportFragmentManager().beginTransaction()
                .remove(resultFragment)
                .commit();
    }
}