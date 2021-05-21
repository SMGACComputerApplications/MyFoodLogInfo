package com.example.myadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Language extends AppCompatActivity {

    TextView lan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        lan=findViewById(R.id.language);
        lan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ;
            }
        });

    }
}
