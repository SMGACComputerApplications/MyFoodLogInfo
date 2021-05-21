package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myadministrator.Model.separatecalculation;
import com.example.myadministrator.ViewHolder.DayHolder;
import com.example.myadministrator.ViewHolder.SelectionFoodView;
import com.example.myadministrator.ViewHolder.SelectionFoodViewtamillist;
import com.example.myadministrator.listener.QuantityChangeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class selectionfoodtamil extends AppCompatActivity {

    DatabaseReference ProductsRef;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<separatecalculation> list;
    SelectionFoodViewtamillist useradapter;
    QuantityChangeListener mQuantityChangeListener;
    String saveCurrentDate,noofdays,userid;
    DatabaseReference userdetails;
    FirebaseDatabase mdatabase;
    FirebaseUser user;
    LinearLayoutManager linearLayoutManager;
    Button b1;
    String day1,month1,year1;
    TextView t1,t2;
    String yourenergy,need;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectionfoodtamil);

        getSupportActionBar().hide();
        Intent in = new Intent(selectionfoodtamil.this, DayHolder.class);
        Intent ina = getIntent();
        noofdays=ina.getStringExtra("noofdaysdetails");

        t1=findViewById(R.id.needenergy);
        t2=findViewById(R.id.yourenergy);

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        mdatabase= FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(selectionfoodtamil.this,finalanswerdisplay.class));

            }
        });

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);


        ProductsRef.child(noofdays).child("TotalEnergy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yourenergy= (String) dataSnapshot.child("totenergy").getValue();
                t2.setText("உங்கள் ஆற்றல் "+yourenergy);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        ProductsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                need= (String) dataSnapshot.child("requiredenergy").getValue();
                t1.setText("உங்களுக்கு தேவையான ஆற்றல்: "+need);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView = findViewById(R.id.userfoodselection);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        ProductsRef.child(noofdays).child("Energy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<separatecalculation>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    separatecalculation f = dataSnapshot1.getValue(separatecalculation.class);
                    list.add(f);
                }
                useradapter=new SelectionFoodViewtamillist(selectionfoodtamil.this,list);
                recyclerView.setAdapter(useradapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });



    }
}
