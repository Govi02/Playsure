package com.hg.playsure;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hg.playsure.Fragments.About;
import com.hg.playsure.Fragments.Category;
import com.hg.playsure.Fragments.Home;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    final Fragment home = new Home();
    final Fragment category = new Category();
    final Fragment about = new About();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.appcolor));
        //initialize the variable for bottom navigation
        bottomNavigation = findViewById(R.id.bottom_Navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_category_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_info_24));

        fm.beginTransaction().add(R.id.fragment_container,about,"3").hide(about).commit();
        fm.beginTransaction().add(R.id.fragment_container,category,"1").hide(category).commit();
        //default fargment
        bottomNavigation.show(2,true);
        fm.beginTransaction().add(R.id.fragment_container,home,"2").commit();
        //Replace fragment as per selection
        bottomNavigation.setOnClickMenuListener(model -> {
            switch (model.getId()) {
                case 1:
                    fm.beginTransaction().hide(active).show(category).commit();
                    active=category;
                    break;
                case 2:
                    fm.beginTransaction().hide(active).show(home).commit();
                    active=home;
                    break;
                case 3:
                    fm.beginTransaction().hide(active).show(about).commit();
                    active=about;
                    break;
            }
            return null;
        });//end of listener

        //Bottomsheet Dialog initialization
        ImageView btmnav = findViewById(R.id.bottom_nav_dialog);
        btmnav.setOnClickListener(v -> showBottomSheetDialog());
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        LinearLayout share = bottomSheetDialog.findViewById(R.id.share);
        LinearLayout rating = bottomSheetDialog.findViewById(R.id.rating);
        LinearLayout contact = bottomSheetDialog.findViewById(R.id.contact);

        share.setOnClickListener(v ->
                {
                    Intent send=new Intent();
                    send.setAction(Intent.ACTION_SEND);
                    send.putExtra(Intent.EXTRA_TEXT,"Welcome to PlaySure..!");
                    send.setType("text/plain");
                    Intent.createChooser(send,"Share Via");
                    startActivity(send);
                });

        rating.setOnClickListener(v -> Toast.makeText(this, "Rating is Clicked", Toast.LENGTH_SHORT).show());
        contact.setOnClickListener(v -> Toast.makeText(this, "Contact is Clicked", Toast.LENGTH_SHORT).show());
        bottomSheetDialog.show();
    }

}