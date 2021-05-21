package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updatebmi extends AppCompatActivity {
    String userid,noofdays;
    String saveCurrentDate;
    DatabaseReference foodadd,mainfoodadd;
    DatabaseReference retrivedata;
    FirebaseUser user;
    FirebaseDatabase mdatabase;
    DatabaseReference userdetails;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    Button b1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatebmi);
        t1=findViewById(R.id.bmiheight);
        t2=findViewById(R.id.bmiweight);
        t3=findViewById(R.id.bmiage);
        t4=findViewById(R.id.bmivalue);
        t5=findViewById(R.id.bmiresult);
        t6=findViewById(R.id.diswaist);
        t7=findViewById(R.id.diship);
        t8=findViewById(R.id.dishealth);
        t9=findViewById(R.id.disbody);
        t10=findViewById(R.id.disenergy);
        mdatabase=FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();

        foodadd=FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        foodadd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a= (String) dataSnapshot.child("height").getValue();
                String b= (String) dataSnapshot.child("weight").getValue();
                String c= (String) dataSnapshot.child("age").getValue();
                String d= (String) dataSnapshot.child("bmivalue").getValue();
                String e= (String) dataSnapshot.child("bmiresult").getValue();
                String f= (String) dataSnapshot.child("waist").getValue();
                String g= (String) dataSnapshot.child("hip").getValue();
                String h= (String) dataSnapshot.child("healthrisk").getValue();
                String i= (String) dataSnapshot.child("bodyshape").getValue();
                String j= (String) dataSnapshot.child("energybmr").getValue();
                t1.setText(a);
                t2.setText(b);
                t3.setText(c);
                t4.setText(d);
                t5.setText(e);
                t6.setText(f);
                t7.setText(g);
                t8.setText(h);
                t9.setText(i);
                t10.setText(j+" (1 Day)");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
