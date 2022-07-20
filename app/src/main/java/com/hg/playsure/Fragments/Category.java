package com.hg.playsure.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hg.playsure.CountDownActivity;
import com.hg.playsure.GamesOnCategory;
import com.hg.playsure.R;

@SuppressWarnings("deprecation")
public class Category extends Fragment {
    private Context globalContext = null;
    TextView action,racing,mind,card,classic;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        setRetainInstance(true);
        globalContext = this.getActivity();

        action = view.findViewById(R.id.action);
        classic = view.findViewById(R.id.classic);
        racing = view.findViewById(R.id.racing);
        mind = view.findViewById(R.id.mind);
        card = view.findViewById(R.id.cards);


        action.setOnClickListener(v -> startActivity(new Intent(globalContext, GamesOnCategory.class)));
        classic.setOnClickListener(v -> startActivity(new Intent(globalContext,GamesOnCategory.class)));
        card.setOnClickListener(v -> startActivity( new Intent(globalContext,   GamesOnCategory.class)));
        racing.setOnClickListener(v -> startActivity(new Intent(globalContext, GamesOnCategory.class)));
        mind.setOnClickListener(v -> startActivity(new Intent(globalContext, GamesOnCategory.class)));

        return view;
    }

}