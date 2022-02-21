package com.example.mypuzzle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    Button playButton;
    Button stopButton;

    Button[][] numbersButton;

    CountDownTimer countDownTimer;
    TextView timerText;
    GridLayout gridLayout;
    TextView movesTex;
    boolean firstGame = false;
    boolean isRestart = true;
    int countsMuves = 0;

    int indexX = 3;
    int indexY = 3;
    boolean isWinn = false;
    SharedPreference preference;

    long time;
    String moves;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);
        timerText = findViewById(R.id.timerTextView);
        gridLayout = findViewById(R.id.gridLayout);
        movesTex = findViewById(R.id.textMoves);

        preference = SharedPreference.getPreference(getApplicationContext());

        name = SharedPreference.getNameUser();

        setTags();

        isWinn = false;
        numbersButton = new Button[4][4];
        start();

        stopButton.setOnClickListener(v -> {
            if (firstGame) {
              /* *//* try {
                    countDownTimer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*//*
                WarnigsdialogShow();*/

                countDownTimer.cancel();
                timerText.setText("00:00");
                movesTex.setText("Moves:000");
                playButton.setText("Play");
                buttonsEnabled();
                endGame();
                firstGame = false;
            }
        });
    }

    private void start() {
        playButton.setOnClickListener(v -> {
            startInit();
            countDownTimer = new CountDownTimer(600000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerText.setText(getTime(millisUntilFinished));
                    time = millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    timerText.setText("00:00");
                    buttonsEnabled();
                    if (isWinn) {
                        Intent intent1 = new Intent(Main2Activity.this, ScoreEnd.class);
                        preference.setScore(name, time, (countsMuves - 1), isWin());
                        finish();
                        startActivity(intent1);
                    } else dialogShow();
                }
            }.start();
        });

    }

    private void startInit() {
        if (isRestart) {
            createMatrix(gridLayout);
            generateNumber();
            playButton.setText("Restart");
            countsMuves = 1;
            indexX = 3;
            indexY = 3;
            isRestart = false;
            firstGame = true;
        } else {
            countDownTimer.cancel();
            createMatrix(gridLayout);
            generateNumber();
            playButton.setText("Restart");
            countsMuves = 1;
            indexX = 3;
            indexY = 3;
            firstGame = true;
        }
    }

    private String getTime(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", (9 - minutes), (59 - seconds));
    }


    private void setTags() {
        int fs = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button bd = (Button) gridLayout.getChildAt(fs++);
                bd.setTag(i + "," + j);
            }
        }
    }


    private void movePlayer(int sdX, int sdY) {
        if (sdX - 1 >= 0 && sdX - 1 == indexX && sdY == indexY) {//top
            Button visiable = numbersButton[sdX][sdY];
            Button unVisable = numbersButton[indexX][indexY];
            numbersButton[indexX][indexY].setText(numbersButton[sdX][sdY].getText());
            visiable.setVisibility(View.INVISIBLE);
            unVisable.setVisibility(View.VISIBLE);
            indexX = sdX;
            indexY = sdY;
            movesTex.setText(String.format(Locale.getDefault(), "Moves:%03d", countsMuves++));
            numbersButton[sdX][sdY].setText("-");
        } else if (sdY - 1 >= 0 && sdX == indexX && sdY - 1 == indexY) {//start
            movesTex.setText(String.format(Locale.getDefault(), "Moves:%03d", countsMuves++));
            numbersButton[indexX][indexY].setText(numbersButton[sdX][sdY].getText());
            Button visiable = numbersButton[sdX][sdY];
            Button unVisable = numbersButton[indexX][indexY];
            numbersButton[indexX][indexY].setText(numbersButton[sdX][sdY].getText());
            visiable.setVisibility(View.INVISIBLE);
            unVisable.setVisibility(View.VISIBLE);
            indexX = sdX;
            indexY = sdY;
            numbersButton[sdX][sdY].setText("-");
        } else if (sdY + 1 < 4 && sdX == indexX && sdY + 1 == indexY) {//end
            Button visiable = numbersButton[sdX][sdY];
            Button unVisable = numbersButton[indexX][indexY];
            numbersButton[indexX][indexY].setText(numbersButton[sdX][sdY].getText());
            visiable.setVisibility(View.INVISIBLE);
            unVisable.setVisibility(View.VISIBLE);
            movesTex.setText(String.format(Locale.getDefault(), "Moves:%03d", countsMuves++));
            numbersButton[indexX][indexY].setText(numbersButton[sdX][sdY].getText());
            indexX = sdX;
            indexY = sdY;
            numbersButton[sdX][sdY].setText("-");
        } else if (sdX + 1 < 4 && sdX + 1 == indexX && sdY == indexY) {//bottom
            Button visiable = numbersButton[sdX][sdY];
            Button unVisable = numbersButton[indexX][indexY];
            numbersButton[indexX][indexY].setText(numbersButton[sdX][sdY].getText());
            visiable.setVisibility(View.INVISIBLE);
            unVisable.setVisibility(View.VISIBLE);
            movesTex.setText(String.format(Locale.getDefault(), "Moves:%03d", countsMuves++));
            numbersButton[indexX][indexY].setText(numbersButton[sdX][sdY].getText());
            indexX = sdX;
            indexY = sdY;
            numbersButton[sdX][sdY].setText("-");
        }
        isWinn = isWin();
        if (isWinn) {
            countDownTimer.cancel();
            preference.setScore(name, time, (countsMuves - 1), isWinn);
            finish();
            Intent intent = new Intent(Main2Activity.this, ScoreEnd.class);
            startActivity(intent);
        }

    }

    private void endGame() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button fss = (Button) gridLayout.getChildAt(i);
            fss.setText("");
            fss.setVisibility(View.VISIBLE);
        }

    }


    private void generateNumber() {
        int[] numbers = new int[15];
        Random rands = new Random();
        for (int i = 0; i < 15; i++) {
            numbers[i] = i + 1;
        }

       for (int i = 0; i < 15; i++) {
            int index = rands.nextInt(numbers.length);
            int temp = numbers[i];
            numbers[i] = numbers[index];
            numbers[index] = temp;
        }
        setButtonsText(numbers);
    }

    private void setButtonsText(int[] numberss) {
        int t = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                numbersButton[i][j].setTag(i + "," + j);
                numbersButton[i][j].setEnabled(true);
                numbersButton[i][j].setVisibility(View.VISIBLE);
                if (t < 15) {
                    numbersButton[i][j].setText(numberss[t++] + "");
                }
            }
        }
        numbersButton[3][3].setText("-");
        numbersButton[3][3].setVisibility(View.INVISIBLE);
        //buttonsDisenabled();
    }

    private void createMatrix(GridLayout gridLayout) {
        int t = 0;
        for (int i = 0; i < 4; i++) {
            for (int i1 = 0; i1 < 4; i1++) {
                numbersButton[i][i1] = (Button) gridLayout.getChildAt(t++);
                numbersButton[i][i1].setOnClickListener(v -> {
                    Button bb = (Button) v;
                    String tag = (String) bb.getTag();
                    String[] ds = tag.split(",");
                    int sdX = Integer.parseInt(ds[0]);
                    int sdY = Integer.parseInt(ds[1]);
                    movePlayer(sdX, sdY);
                });
            }
        }
    }


    public boolean isWin() {
        int t = 1, j = 0;
        for (Button[] button1 : numbersButton) {
            for (Button button2 : button1) {
                if (button2.getText().equals(t++ + "")) {
                    j++;
                }

            }

        }
        return j == 15;
    }

    private void buttonsEnabled() {
        for (Button[] button1 : numbersButton) {
            for (Button button2 : button1) {
                button2.setEnabled(false);
            }
        }
    }

    public void dialogShow() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Ooh you lose");
        alert.setMessage("Please try again");
        alert.setPositiveButton("Retry", (dialog, which) -> playButton.callOnClick());
        alert.show();
    }

    public void WarnigsdialogShow() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warnings");
        alert.setMessage("Are you going to stop game?");
        alert.setPositiveButton("Yes", (dialog, which) -> {
            countDownTimer.cancel();
            timerText.setText("00:00");
            movesTex.setText("Qadam:000");
            playButton.setText("Play");
            buttonsEnabled();
            endGame();
            firstGame = false;
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // countDownTimer.start();
            }
        });
        alert.show();
    }
/*
 @Override
    public boolean isWinner() {
        return isWinn;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getMoves() {
        return moves;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public String getName() {
        return "Salom";
    }
*/

}
