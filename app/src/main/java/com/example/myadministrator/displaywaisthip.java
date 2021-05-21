package com.example.myadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class displaywaisthip extends AppCompatActivity {
    String userid,noofdays;
    String saveCurrentDate;
    DatabaseReference foodadd,mainfoodadd;
    DatabaseReference retrivedata;
    FirebaseUser user;
    FirebaseDatabase mdatabase;
    DatabaseReference userdetails;
    TextView t1,t2,t3,t4,t5;
    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaywaisthip);


    }
}
