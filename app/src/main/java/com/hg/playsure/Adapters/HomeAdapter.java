package com.hg.playsure.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.hg.playsure.Models.MostPlayedModel;
import com.hg.playsure.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<MostPlayedModel> mostPlayedArray;
    private RecyclerViewClickListener listener;

    public HomeAdapter(Context ctx, ArrayList<MostPlayedModel> mostPlayedArray, RecyclerViewClickListener listener){

        inflater = LayoutInflater.from(ctx);
        this.mostPlayedArray = mostPlayedArray;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.home_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Picasso.get().load(mostPlayedArray.get(position).getImgURL()).into(holder.iv);
        holder.name.setText(mostPlayedArray.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mostPlayedArray.size();
    }

    public interface RecyclerViewClickListener{
        void onclick(View v,int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView name;
        ImageView iv;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            name.setSelected(true);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            //Click Listener for each element in RecyclerView
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listener.onclick(v,getAdapterPosition());
        }
    }
}