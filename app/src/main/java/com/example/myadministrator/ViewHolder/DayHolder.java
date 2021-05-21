package com.example.myadministrator.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadministrator.Finalquestion;
import com.example.myadministrator.Model.Daywisedetails;
import com.example.myadministrator.R;
import com.example.myadministrator.preuserviewfooddetails;
import com.example.myadministrator.userviewfooddetails;
import com.example.myadministrator.userviewfooddetails1;

import java.util.ArrayList;

public class DayHolder extends RecyclerView.Adapter<DayHolder.Daymyholder> {
    Context context;
    ArrayList<Daywisedetails> daywises;

    public DayHolder(Context c, ArrayList<Daywisedetails> d) {
        this.context = c;
        this.daywises = d;
    }

    @NonNull
    @Override
    public Daymyholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.daywise,parent,false);
        return new Daymyholder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Daymyholder holder, final int position) {

                         holder.dayer.setText(daywises.get(position).getDayde());
                         holder.itemView.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 Intent intent = new Intent(context, preuserviewfooddetails.class);
                                 intent.putExtra("noofdaysdetails",daywises.get(position).getDayde());
                                 context.startActivity(intent);

                             }
                         });
    }

    @Override
    public int getItemCount() {
        return daywises.size();
    }

    public class Daymyholder extends RecyclerView.ViewHolder {
        TextView dayer;

        public Daymyholder(@NonNull View itemView) {
            super(itemView);

            dayer=(TextView)itemView.findViewById(R.id.daydetails1);
        }
    }
}
