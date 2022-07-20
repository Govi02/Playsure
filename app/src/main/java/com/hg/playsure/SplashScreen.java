package com.hg.playsure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("deprecation")
public class SplashScreen extends AppCompatActivity {
    LinearProgressIndicator progressIndicator;
    int progressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setStatusBarColor(getResources().getColor(R.color.appcolor));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable bg = SplashScreen.this.getResources().getDrawable(R.drawable.gradient1);
        getWindow().setBackgroundDrawable(bg);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
        progress();
    }


    public void progress() {

        progressIndicator = findViewById(R.id.pb);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                progressStatus++;
                progressIndicator.setProgress(progressStatus);
                if (progressStatus == 100)
                    t.cancel();
            }
        };
        t.schedule(tt, 0, 100);
    }
}