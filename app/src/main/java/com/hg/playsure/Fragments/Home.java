package com.hg.playsure.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.hg.playsure.API_Config;
import com.hg.playsure.CountDownActivity;
import com.hg.playsure.GameLoader;
import com.hg.playsure.Adapters.HomeAdapter;
import com.hg.playsure.Models.MostPlayedModel;
import com.hg.playsure.No_internet;
import com.hg.playsure.R;
import com.hg.playsure.Screen_one;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


@SuppressWarnings("deprecation")
public class Home extends Fragment {

    private Context globalContext = null;
    String gameURL;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    ShimmerFrameLayout loader;
    ShimmerFrameLayout loader1;

    ArrayList<MostPlayedModel> mostPlayedArrayList = new ArrayList<>();
    ArrayList<MostPlayedModel> popularArrayList = new ArrayList<>();
    private HomeAdapter mpAdapter;
    private RecyclerView most_Played_RV, popular_RV;
    private HomeAdapter.RecyclerViewClickListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setRetainInstance(true);
        globalContext = this.getActivity();

        most_Played_RV = view.findViewById(R.id.most_played_recycler);
        popular_RV = view.findViewById(R.id.popular_recycler);
//                  snackbar = Snackbar.make(coordinatorLayout,"Please Check Your Internet Connection...!",Snackbar.LENGTH_LONG);
//                snackbar.setTextColor(Color.WHITE);
//                View sbview=snackbar.getView();
//                sbview.setBackgroundResource(R.drawable.play);
//                snackbar.show();


        //Initialization of Mobile Ad SDK
        MobileAds.initialize(globalContext, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
//                Toast.makeText(globalContext, "Ad loaded successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        //Banner AD 1
        AdRequest adRequest=new AdRequest.Builder().build();
        AdView adview1=view.findViewById(R.id.adbanner1);
        AdView adview2=view.findViewById(R.id.adbanner2);
        AdView adview3=view.findViewById(R.id.adbanner3);
        adview1.loadAd(adRequest);
        adview2.loadAd(adRequest);
        adview3.loadAd(adRequest);

        loader = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        loader1= (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container2);

        fetchingJSON();

        if (!checkInternet()) {
            Intent i = new Intent(globalContext, No_internet.class);
            startActivity(i);
        } else {
            Log.e("TAG", "Internet Connection" + checkInternet());
        }

        sharedPreferences = globalContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
        if (isItFirestTime()) {
            Intent i = new Intent(globalContext, Screen_one.class);
            startActivity(i);
        } else {
            Log.e("TAG", "It's not First Time :" + isItFirestTime());
        }
        return view;
    }

    private void fetchingJSON() {
        loader.startShimmer();
        loader1.startShimmer();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_Config.allgames,
                response -> {
                    loader.stopShimmer();
                    loader1.stopShimmer();
                    loader.setVisibility(View.GONE);
                    loader1.setVisibility(View.GONE);
                    Log.d("strrrrr", ">>" + response);

                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.optString("status").equals("true")) {

                            JSONArray dataArray = obj.getJSONArray("data");

                            for (int i = 0; i < dataArray.length(); i++) {

                                MostPlayedModel playerModel = new MostPlayedModel();
                                JSONObject dataobj = dataArray.getJSONObject(i);
//                                if (dataobj.getString("name").equals("Subway Surfers")) {
                                    playerModel.setName(dataobj.getString("name"));
                                    playerModel.setImgURL(dataobj.getString("imgURL"));
                                    gameURL = dataobj.getString("gameURL");
                                    mostPlayedArrayList.add(playerModel);
                                    popularArrayList.add(playerModel);
//                                }
//                                if (dataobj.getString("name").equals("Subway Surfers")) {
//                                    playerModel.setName(dataobj.getString("name"));
//                                    playerModel.setImgURL(dataobj.getString("imgURL"));
//                                    gameURL = dataobj.getString("gameURL");
//                                    popularArrayList.add(playerModel);
//                                }
                            }
                            setupRecycler();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(globalContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(globalContext);
        requestQueue.add(stringRequest);

    }

    private void setupRecycler() {
        setOnClickListener();
        //most played
        mpAdapter = new HomeAdapter(globalContext, mostPlayedArrayList, listener);
        most_Played_RV.setAdapter(mpAdapter);
        most_Played_RV.setLayoutManager(new GridLayoutManager(globalContext, 10));
        //popular
        mpAdapter = new HomeAdapter(globalContext, popularArrayList, listener);
        popular_RV.setAdapter(mpAdapter);
        popular_RV.setLayoutManager(new GridLayoutManager(globalContext, 10));
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Log.e("TAG", "gameURL : " + gameURL);
            Intent intent = new Intent(globalContext, GameLoader.class);
            intent.putExtra("WEB_PASSING", gameURL);
            startActivity(intent);
        };
    }

    public boolean isItFirestTime() {
        if (sharedPreferences.getBoolean("firstTime", true)) {
            sharedEditor.putBoolean("firstTime", false);
            sharedEditor.commit();
            sharedEditor.apply();
            return true;
        } else {
            return false;
        }
    }

    public boolean checkInternet() {
        ConnectivityManager manager = (ConnectivityManager) globalContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}