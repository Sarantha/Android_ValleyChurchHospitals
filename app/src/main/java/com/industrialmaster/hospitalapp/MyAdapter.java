package com.industrialmaster.hospitalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Profile> profiles;

    public MyAdapter(Context c, ArrayList<Profile> p){
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dName.setText(profiles.get(position).getName());
        holder.dSpecial.setText(profiles.get(position).getSpecial());
        holder.dDate.setText(profiles.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dName,dDate,dSpecial;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dName = (TextView) itemView.findViewById(R.id.doctorName);
            dSpecial = (TextView) itemView.findViewById(R.id.specializedArea);
            dDate = (TextView) itemView.findViewById(R.id.doctorDate);
        }
    }
}
