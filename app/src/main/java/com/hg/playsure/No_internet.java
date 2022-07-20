package com.hg.playsure;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("deprecation")
public class No_internet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        //set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.appcolor));
        TextView btn =findViewById(R.id.retry);
        btn.setOnClickListener(v -> {
            Intent i = new Intent(No_internet.this, MainActivity.class);
            startActivity(i);
        });
    }
}
