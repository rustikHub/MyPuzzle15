package com.example.mypuzzle;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Button clear;

    SharedPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        preference = SharedPreference.getPreference(getApplicationContext());

        clear = findViewById(R.id.buttonClear);

       clear.setOnClickListener(v -> dialogShow());
    }
    public void dialogShow() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warnings");
        alert.setMessage("You are going to clear all scores");
        alert.setPositiveButton("ok", (dialog, which) -> preference.clearAll(getApplicationContext()));
        alert.setNegativeButton("cancel", (dialog, which) -> Toast.makeText(getApplicationContext(),"Cenceled",Toast.LENGTH_SHORT).show());

        alert.show();
    }

}
