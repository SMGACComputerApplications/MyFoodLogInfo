package com.example.myadministrator.ViewHolder;
import com.example.myadministrator.Finalquestion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadministrator.Model.FoodList;
import com.example.myadministrator.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodViewHolder extends RecyclerView.Adapter<FoodViewHolder.MyViewHolder>{
    Context context;
    ArrayList<FoodList> foodRecipes;

    public FoodViewHolder(Context c, ArrayList<FoodList> f) {
        this.context = c;
        this.foodRecipes = f;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.foodlistview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtRecipeName.setText(foodRecipes.get(position).getRecipename());
                String g=foodRecipes.get(position).getImage();
        Toast.makeText(context, "Image : "+g, Toast.LENGTH_SHORT).show();
        Picasso.get().load(foodRecipes.get(position).getImage()).into(holder.Food_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Finalquestion.class);
                context.startActivity(intent);








            }
        });

    }

    @Override
    public int getItemCount() {
        return foodRecipes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtRecipeName;
        ImageView Food_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRecipeName=(TextView)itemView.findViewById(R.id.foodcard_name);
            Food_image=(ImageView)itemView.findViewById(R.id.foodcard_image);

        }

    }

}
