package com.industrialmaster.hospitalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAppointmentsAdapter extends RecyclerView.Adapter<MyAppointmentsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Appointments> appointments;

    public MyAppointmentsAdapter(Context c,ArrayList<Appointments> a){
        context = c;
        appointments = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.appointmentscardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.appointmnetHolder.setText(appointments.get(position).getName());
        holder.appointmentDate.setText(appointments.get(position).getBookDate());
        holder.appointmentDoctor.setText(appointments.get(position).getDoctor());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView appointmnetHolder,appointmentDate,appointmentDoctor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            appointmnetHolder = (TextView) itemView.findViewById(R.id.patientName);
            appointmentDate = (TextView) itemView.findViewById(R.id.bookedOn);
            appointmentDoctor = (TextView) itemView.findViewById(R.id.doctorBooked);
        }
    }
}
