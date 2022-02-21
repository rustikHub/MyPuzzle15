package com.example.mypuzzle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ScoreActivity extends AppCompatActivity {

    private LinearLayout layout;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        sharedPreference = SharedPreference.getPreference(getApplicationContext());
        ArrayList<String> scores = sharedPreference.getAllScore();
        layout = findViewById(R.id.parentScrole);

//Toast.makeText(getApplicationContext(),scores.size(),Toast.LENGTH_LONG).show();
        if (!scores.isEmpty()) {
            for (int i = 0; i < scores.size(); i++) {
                LinearLayout layoutText = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.scores_scrol, layout, false);
                TextView nameView = layoutText.findViewById(R.id.scoreName);
                TextView timeView = layoutText.findViewById(R.id.scoreTime);
                TextView movesView = layoutText.findViewById(R.id.scoreMoves);

                String score = scores.get(i);
                String[] info = score.split("#");

                String name = info[1];
                String minut = info[2];
                String moves = info[3];
                TextView numbers = layoutText.findViewById(R.id.textNumbers);

                numbers.setText(String.valueOf(i + 1));
                nameView.setText(name);
                timeView.setText(String.format("Time : %s", getTime(Long.parseLong(minut))));
                movesView.setText(String.format("Moves : %s",moves));
                layout.addView(layoutText);

            }

        } else {
            LinearLayout layoutText = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.scores_scrol, layout, false);
            TextView nameView = layoutText.findViewById(R.id.scoreName);
            TextView timeView = layoutText.findViewById(R.id.scoreTime);
            TextView movesView = layoutText.findViewById(R.id.scoreMoves);
            TextView numbersText = layoutText.findViewById(R.id.textNumbers);
            timeView.setVisibility(View.GONE);
            movesView.setVisibility(View.GONE);
            numbersText.setVisibility(View.GONE);
            nameView.setText("You don't have any scores");
            layout.addView(layoutText);
        }
    }

    private String getTime(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", (9 - minutes), (59 - seconds));
    }
}
