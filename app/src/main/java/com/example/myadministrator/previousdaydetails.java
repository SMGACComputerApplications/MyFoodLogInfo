package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myadministrator.Model.Daywisedetails;
import com.example.myadministrator.ViewHolder.DayHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class previousdaydetails extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase mdatabase;
    DatabaseReference userdetails;
    String userid;
    DatabaseReference ProductsRef;
    RecyclerView recyclerView;
    ArrayList<Daywisedetails> list;
    DayHolder useradapter;

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previousdaydetails);
        mdatabase = FirebaseDatabase.getInstance();
        userdetails = mdatabase.getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Days");
        recyclerView = findViewById(R.id.daywisedetails23);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<Daywisedetails>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Daywisedetails f=dataSnapshot1.getValue(Daywisedetails.class);
                    list.add(f);
                }
                useradapter=new DayHolder(previousdaydetails.this,list);
                recyclerView.setAdapter(useradapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(previousdaydetails.this, ""+databaseError, Toast.LENGTH_SHORT).show();


            }
        });

          }
}
