package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userhome extends AppCompatActivity {
TextView t0,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;
    private DatabaseReference UserRef;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        t0=findViewById(R.id.username);
        t1=findViewById(R.id.userage1);
        t2=findViewById(R.id.userheight1);
        t3=findViewById(R.id.userweight1);
        t4=findViewById(R.id.userbmivalue);
        t5=findViewById(R.id.userwaist);
        t6=findViewById(R.id.userhip1);
        t7=findViewById(R.id.userwaisthipratio);
        t8=findViewById(R.id.userhealthrisk);
        t9=findViewById(R.id.userbodyshape);
        t10=findViewById(R.id.userenergybmr);
        t11=findViewById(R.id.userneededenergy);
        Intent in = new Intent(Userhome.this, Finalquestion.class);
        Intent ina = getIntent();
        userid = ina.getStringExtra("userid");
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
UserRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        t0.setText(dataSnapshot.child("username").getValue().toString());
        t1.setText(dataSnapshot.child("age").getValue().toString());
        t2.setText(dataSnapshot.child("height").getValue().toString());
        t3.setText(dataSnapshot.child("weight").getValue().toString());
        t4.setText(dataSnapshot.child("bmivalue").getValue().toString());
        t5.setText(dataSnapshot.child("waist").getValue().toString());
        t6.setText(dataSnapshot.child("hip").getValue().toString());
        t7.setText(dataSnapshot.child("waisthipsize").getValue().toString());
        t8.setText(dataSnapshot.child("healthrisk").getValue().toString());
        t9.setText(dataSnapshot.child("bodyshape").getValue().toString());
        t10.setText(dataSnapshot.child("energybmr").getValue().toString());
        t11.setText(dataSnapshot.child("requiredenergy").getValue().toString());
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
