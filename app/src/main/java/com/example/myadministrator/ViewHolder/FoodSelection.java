package com.example.myadministrator.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadministrator.Model.FoodList;
import com.example.myadministrator.Model.SelectFood;
import com.example.myadministrator.Model.selectingFoodview;
import com.example.myadministrator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FoodSelection extends RecyclerView.Adapter<FoodSelection.FoodHolder> {
    String saveCurrentDate,noofdays,userid;
    Context context;
    ArrayList<FoodList> foodRecipes1;
    DatabaseReference userdetails;
    FirebaseDatabase mdatabase;
    FirebaseUser user;

    public FoodSelection(Context context, ArrayList<FoodList> foodRecipes1) {
        this.context = context;
        this.foodRecipes1 = foodRecipes1;
    }


    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.selectingfooditem,parent,false);
          return new FoodHolder(v);
         }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {

        holder.txtRecipeName1.setText(foodRecipes1.get(position).getRecipename());
        Picasso.get().load(foodRecipes1.get(position).getImage()).into(holder.Food_image1);


    }

    @Override
    public int getItemCount() {
        return foodRecipes1.size();
    }

    public class FoodHolder extends RecyclerView.ViewHolder {

        TextView txtRecipeName1;
        ImageView Food_image1;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);

            txtRecipeName1=(TextView)itemView.findViewById(R.id.selectuserfood_name);
            Food_image1=(ImageView)itemView.findViewById(R.id.userselectfood_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
