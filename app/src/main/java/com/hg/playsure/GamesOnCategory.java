package com.hg.playsure;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.hg.playsure.Adapters.CategoryAdapter;
import com.hg.playsure.Helpers.AutoFitGridLayoutManager;
import com.hg.playsure.Models.MostPlayedModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GamesOnCategory extends AppCompatActivity {
    private CategoryAdapter cAdapter;
    private RecyclerView category_RV;
    private CategoryAdapter.RecyclerViewClickListener listener;
    ArrayList<MostPlayedModel> categoryArrayList = new ArrayList<>();
    String gameURL;
    ImageButton backbutton;
    ShimmerFrameLayout loader1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_on_category);
        //set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.appcolor));
        //backbutton
        backbutton = findViewById(R.id.back);
        backbutton.setOnClickListener(v -> onBackPressed());
        //recycler data fetching
        category_RV = findViewById(R.id.category_recycler);
        loader1= (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container2);
        fetchingJSON();
    }

    private void fetchingJSON() {
        loader1.startShimmer();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_Config.allgames,
                response -> {
                    loader1.stopShimmer();
                    loader1.setVisibility(View.GONE);
                    Log.d("strrrrr", ">>" + response);

                    try {

                        JSONObject obj = new JSONObject(response);
                        if (obj.optString("status").equals("true")) {

                            JSONArray dataArray = obj.getJSONArray("data");

                            for (int i = 0; i < dataArray.length(); i++) {

                                MostPlayedModel playerModel = new MostPlayedModel();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                if (dataobj.getString("name").equals("Subway Surfers")) {
                                    playerModel.setName(dataobj.getString("name"));
                                    playerModel.setImgURL(dataobj.getString("imgURL"));
                                    gameURL = dataobj.getString("gameURL");
                                    categoryArrayList.add(playerModel);
                                }
                            }
                            setupRecycler();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void setupRecycler() {
        setOnClickListener();
        cAdapter = new CategoryAdapter(this, categoryArrayList, listener);
        category_RV.setAdapter(cAdapter);
        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 220);
        category_RV.setLayoutManager(layoutManager);

    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Log.e("TAG", "gameURL : " + gameURL);
            Intent intent = new Intent(this, GameLoader.class);
            intent.putExtra("WEB_PASSING", gameURL);
            startActivity(intent);
        };
    }
}