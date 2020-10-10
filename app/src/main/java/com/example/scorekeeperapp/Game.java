package com.example.scorekeeperapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*

Author @ Marwa hatamleh
 */
public class Game extends AppCompatActivity {
    static final long START_TIMER_MILLIS = 60000;
    int teamScoreA ;
    int teamScoreB ;
    String scoreA = "     ";
    String scoreB = "     ";
    long TimenMillis = START_TIMER_MILLIS;
    TextView ScoreTeamone;
    TextView ScoreTeamtwo;
    TextView Counter;
    Button Reset;
    EditText editteamone;
    EditText editeamtwo;
    CountDownTimer CountTimer;
    AlertDialog.Builder builder;
    boolean TimerRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView Date = (TextView) findViewById(R.id.date);
        Counter = (TextView) findViewById(R.id.timer);
        ScoreTeamone = findViewById(R.id.scoreone);
        ScoreTeamtwo = findViewById(R.id.scoretwo);
        editteamone = findViewById(R.id.scoresteamengland);
        editeamtwo = findViewById(R.id.scoresteamgermany);
        builder = new AlertDialog.Builder(this);
        Reset = findViewById(R.id.reset);
        editteamone.setVerticalScrollBarEnabled(true);
        editteamone.setMovementMethod(new ScrollingMovementMethod());
        editteamone.setKeyListener(null);
        editeamtwo.setVerticalScrollBarEnabled(true);
        editeamtwo.setMovementMethod(new ScrollingMovementMethod());
        editeamtwo.setKeyListener(null);
        startTimer();
        String date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        Date.setText(date);
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer() {
        CountTimer = new CountDownTimer(TimenMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimenMillis = millisUntilFinished;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                TimerRun = false;
                MediaPlayer ring = MediaPlayer.create(Game.this, R.raw.buzzer);
                ring.start();
                scoreObserver();
            }
        }.start();

        TimerRun = true;
    }

    private void updateCountDown() {
        int min = (int) (TimenMillis / 1000) / 60;
        int sec = (int) (TimenMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        Counter.setText(timeLeftFormatted);
    }

    private void scoreObserver() {
        if (teamScoreA == teamScoreB) {

            win("No One  ", teamScoreA);

        } else if (teamScoreA > teamScoreB) {

            win("England ", teamScoreA);
        } else {

            win("Germany ", teamScoreB);
        }
    }

    private void win(String s, int i) {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.MyDialogStyle);
        builder1.setTitle("Congratulations" + " " + s + " Won." + " " + "The score is " + i).
                setIcon(R.drawable.ic_baseline_sports_soccer_24);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Play Again!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Game.this, MainActivity.class));
                        resetTimer();
                    }
                });

        AlertDialog alert = builder1.create();
        alert.show();

    }


    private void resetTimer() {
        TimenMillis = START_TIMER_MILLIS;
        CountTimer.cancel();
        teamScoreA = 0;
        teamScoreB = 0;
        scoreA = "     ";
        scoreB = "     ";
        ScoreTeamone.setText(teamScoreA + "");
        editteamone.setText(scoreA);
        editeamtwo.setText(scoreB);
        ScoreTeamtwo.setText(teamScoreA + "");
        startTimer();
    }

    public void Goalteamone(View view) {
        MediaPlayer ring = MediaPlayer.create(Game.this, R.raw.crowd);
        ring.start();
        teamScoreA += 3;
        ScoreTeamone.setText(teamScoreA + " ");
        scoreA += "GOAL" + "   ";
        editteamone.setText(scoreA);

    }

    public void Goalteamtwo(View view) {
        MediaPlayer ring = MediaPlayer.create(Game.this, R.raw.crowd);
        ring.start();
        teamScoreB += 3;
        ScoreTeamtwo.setText(teamScoreB + " ");
        scoreB += "GOAL" + "   ";
        editeamtwo.setText(scoreB);

    }

    public void yallowcone(View view) {
        MediaPlayer ring = MediaPlayer.create(Game.this, R.raw.referee);
        ring.start();
        scoreA += "Yallow Card" + "    ";
        editteamone.setText(scoreA);

    }

    public void yallowctwo(View view) {
        MediaPlayer ring = MediaPlayer.create(Game.this, R.raw.referee);
        ring.start();
        scoreB += "Yallow Card" + "    ";
        editeamtwo.setText(scoreB);


    }

    public void redcone(View view) {
        MediaPlayer ring = MediaPlayer.create(Game.this, R.raw.referee);
        ring.start();
        scoreA += "Red Card" + "   ";
        editteamone.setText(scoreA);
        Toast.makeText(getApplicationContext(), "GET OUT !!!", Toast.LENGTH_SHORT).show();
    }

    public void redctwo(View view) {
        MediaPlayer ring = MediaPlayer.create(Game.this, R.raw.referee);
        ring.start();
        scoreB += "Red Card" + "   ";
        editeamtwo.setText(scoreB);
        Toast.makeText(getApplicationContext(), "GET OUT !!!", Toast.LENGTH_SHORT).show();
    }

}