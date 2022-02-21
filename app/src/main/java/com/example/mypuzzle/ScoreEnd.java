package com.example.mypuzzle;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ScoreEnd extends AppCompatActivity {

    String id;
    String moves;
    String time;
    String name;
    String isWinn;
    TextView nameText;
    TextView timeText;
    TextView movesText;
    TextView isWinTExt;
    Button restart;

    LinearLayout scrolLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_end);

        SharedPreference preference = SharedPreference.getPreference(null);
        String[] sd = SharedPreference.getNameUser().split("#");
        id = sd[1];

        scrolLayout = findViewById(R.id.scroleEndActivity);
        restart = findViewById(R.id.scorEndREstart);

        restart.setOnClickListener(v -> {
            Intent intent1 = new Intent(ScoreEnd.this, Main2Activity.class);
            SharedPreference.setNameUser(sd[0]);
            startActivity(intent1);
            finish();
        });

        nameText = findViewById(R.id.nameText);
        timeText = findViewById(R.id.timetex);
        movesText = findViewById(R.id.movestext);
        isWinTExt = findViewById(R.id.winTabrik);

        ArrayList<String> allScore = preference.getAllScore();
        String[] info = preference.getById(id).split("#");


        name = info[1];
        time = info[2];
        moves = info[3];
        isWinn = info[4];

        isWinTExt.setText("Wow You win!!");
        nameText.setText(name);
        timeText.setText(getTime(Long.parseLong(time)));
        movesText.setText(String.format("Moves : %s",moves));


        if (!allScore.isEmpty()) {
            for (int i = 0; i < allScore.size(); i++) {

                LinearLayout lyouts = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.scores_scroll_mini, scrolLayout, false);
                String[] playersInfo = allScore.get(i).split("#");
                String playerName = playersInfo[1];
                String playerTime = playersInfo[2];
                String playerMoves = playersInfo[3];
                String idPlayer = playersInfo[0];

                TextView name = lyouts.findViewById(R.id.scoreName);
                TextView time = lyouts.findViewById(R.id.scoreTime);
                TextView movesT = lyouts.findViewById(R.id.scoreMoves);
                TextView numbers = lyouts.findViewById(R.id.textNumbers);

                if (id.equals(idPlayer)) {
                    lyouts.setBackgroundResource(R.drawable.ramka_hint);
                    numbers.setBackgroundResource(R.drawable.ramka2_hint);
                }
                numbers.setText(String.valueOf(i + 1));
                name.setText(playerName);
                time.setText(getTime(Long.parseLong(playerTime)));
                movesT.setText(String.format("Moves : %s",playerMoves));

                scrolLayout.addView(lyouts);

            }
        }


    }

    private String getTime(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", (9 - minutes), (59 - seconds));
    }
}
