package com.example.myadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class fooddescription extends AppCompatActivity {
    ImageView im1;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddescription);
        im1=findViewById(R.id.circlefoodview);
         s=getIntent().getStringExtra("image");
         Picasso.get().load(s).into(im1);


    }
}
