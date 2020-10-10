package com.example.scorekeeperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
/*

Author @ Marwa Hatamleh
 */
public class MainActivity extends AppCompatActivity {

    Timer timer;
    ProgressBar progressBar;
    int c = 0;
    TextView Text;
    static int SPLASH_TIME_OUT = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.SplashprogressBar);
        progressBar.setProgress(0);
        Text = findViewById(R.id.splashTimer);
        Text.setText("");

        final long period = 60;

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (c < 100) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Text.setText(String.valueOf(c) + "%");
                        }
                    });
                    progressBar.setProgress(c);

                    c++;

                } else {

                    timer.cancel();

                    Intent intent = new Intent(MainActivity.this, game.class);
                    finish();

                }
            }
        }, 0, period);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(MainActivity.this, game.class);
                startActivity(splashIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}