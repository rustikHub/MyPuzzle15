package com.example.mypuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    int backPressCount = 0;
    LinearLayout playLayout;
    LinearLayout scoreLayout;
    LinearLayout settingsLayout;
    LinearLayout exitLayout;
    EditText playerEdit;

    boolean isEmpty;
    String playerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playLayout = findViewById(R.id.playLayout);
        scoreLayout = findViewById(R.id.scoresLayout);
        settingsLayout = findViewById(R.id.settings);
        exitLayout = findViewById(R.id.exit_button);
        playerEdit = findViewById(R.id.edit_textPlayer);

    }


    @Override
    public void onBackPressed() {

        if (backPressCount > 0)
            super.onBackPressed();
        else {
            backPressCount++;
            if (backPressCount == 1) {
                Toast.makeText(this, "Dasturni yopish uchun qayta bosing !", Toast.LENGTH_SHORT).show();
            }
            new CountDownTimer(4000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    backPressCount = 0;
                }
            }.start();
        }
    }


    public void exit(View view) {
        finish();
    }

    public void play(View view) {
        playerName = playerEdit.getText().toString().trim();
        isEmpty = playerName.isEmpty();
        if (isEmpty) {
            Toast.makeText(getApplicationContext(), "Please, enter your name", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MenuActivity.this, Main2Activity.class);
            SharedPreference.setNameUser(playerName);
            startActivity(intent);
        }
    }

    public void scores(View view) {
        Intent intent = new Intent(MenuActivity.this, ScoreActivity.class);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}
