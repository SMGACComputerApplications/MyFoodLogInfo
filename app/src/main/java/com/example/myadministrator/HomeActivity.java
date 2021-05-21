package com.example.myadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    public static int TIME_OUT=2000;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        b1=findViewById(R.id.butenglish);
        b2=findViewById(R.id.buttamil);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,Main2Activity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}
