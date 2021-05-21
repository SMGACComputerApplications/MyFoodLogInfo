package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myadministrator.Fragments.BreakfastFragment;
import com.example.myadministrator.Model.FoodList;
import com.example.myadministrator.Model.SelectFood;
import com.example.myadministrator.Model.UserFoodList;
import com.example.myadministrator.Model.selectingFoodview;
import com.example.myadministrator.ViewHolder.FoodSelection;
import com.example.myadministrator.ViewHolder.SelectFoodViewHolder;
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

public class Userfoodselecteditemlist extends AppCompatActivity {

    DatabaseReference ProductsRef;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<SelectFood> list;
    SelectFoodViewHolder useradapter;
    QuantityChangeListener mQuantityChangeListener;
    String saveCurrentDate,noofdays,userid;
    DatabaseReference userdetails;
    FirebaseDatabase mdatabase;
    FirebaseUser user;
    Button b1;
    LinearLayoutManager linearLayoutManager;
TextView c1,c2,c3,c4,c5,c6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfoodselecteditemlist);
getSupportActionBar().hide();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        noofdays=saveCurrentDate.toString();
        mdatabase= FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        recyclerView = findViewById(R.id.selectfood);
        recyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, 2);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Userfoodselecteditemlist.this,userviewfooddetails.class));
            }
        });

        recyclerView.setLayoutManager(mGridLayoutManager);


        ProductsRef.child(noofdays).child("Food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<SelectFood>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    SelectFood f=dataSnapshot1.getValue(SelectFood.class);
                    list.add(f);
                }
                useradapter=new SelectFoodViewHolder(Userfoodselecteditemlist.this,list);
                recyclerView.setAdapter(useradapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Userfoodselecteditemlist.this, ""+databaseError, Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
    }


}
