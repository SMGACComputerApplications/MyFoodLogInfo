package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myadministrator.Model.FoodList;
import com.example.myadministrator.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewFoodDetails extends AppCompatActivity {
     DatabaseReference ProductsRef;
private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    ArrayList<FoodList> list;
    FoodViewHolder adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_details);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("MainFood");
        recyclerView = findViewById(R.id.foodlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         ProductsRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        list=new ArrayList<FoodList>();

        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
            FoodList f=dataSnapshot1.getValue(FoodList.class);
            list.add(f);

        }
        adapter=new FoodViewHolder(ViewFoodDetails.this,list);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }

    @Override
    protected void onStart() {
        super.onStart();

        }
}
