package com.hg.playsure;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CountDownActivity extends AppCompatActivity {

    private TextView mTextView;
    public int counter=5;
    String gameURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        //set status bar color
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable bg = CountDownActivity.this.getResources().getDrawable(R.drawable.gradient1);
        getWindow().setBackgroundDrawable(bg);

        mTextView = (TextView) findViewById(R.id.text);
        //get string from home activity
        gameURL = getIntent().getStringExtra("WEB_PASSING");

        new CountDownTimer(5000,1000){
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                mTextView.setText("Continue in "+ counter);
                counter--;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                mTextView.setText("Click to Play");
                mTextView.setOnClickListener(v->
                {
                    Intent i = new Intent(CountDownActivity.this, GameLoader.class);
                    i.putExtra("WEB_PASSING", gameURL);
                    startActivity(i);
                });
            }
        }.start();

        //Intent

    }
}