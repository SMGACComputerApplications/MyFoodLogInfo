package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myadministrator.Model.SelectFood;
import com.example.myadministrator.Model.separatecalculation;
import com.example.myadministrator.ViewHolder.SelectFoodViewHolder;
import com.example.myadministrator.ViewHolder.SelectionFoodView;
import com.example.myadministrator.listener.QuantityChangeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.myadministrator.bmivisual.enerycalc;

public class userviewfooddetails extends AppCompatActivity {

    DatabaseReference ProductsRef;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<separatecalculation> list;
    SelectionFoodView useradapter;
    QuantityChangeListener mQuantityChangeListener;
    String saveCurrentDate,noofdays,userid;
    DatabaseReference userdetails;
    FirebaseDatabase mdatabase;
    FirebaseUser user;
    LinearLayoutManager linearLayoutManager;
    Button b1;
    TextView t1,t2;
    String lan;
    String energycalclator;
String yourenergy,need;
float youren,needen;
AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewfooddetails);
          getSupportActionBar().hide();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        noofdays=saveCurrentDate.toString();
        t1=findViewById(R.id.needenergy);
        t2=findViewById(R.id.yourenergy);
        mdatabase= FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();
        builder=new AlertDialog.Builder(this);

        FloatingActionButton fab = findViewById(R.id.fab);


        SharedPreferences setting= PreferenceManager.getDefaultSharedPreferences(this);
        lan=setting.getString("Language","");

        SharedPreferences setting2= PreferenceManager.getDefaultSharedPreferences(this);

        String weight=setting2.getString("weight","");
        String height=setting2.getString("height","");
        String waist=setting2.getString("waist","");
        String hip=setting2.getString("hip","");
        String age=setting2.getString("age","");
        String gen=setting2.getString("gender","");



        if(gen.equals("Male")|| gen.equals("ஆண்")){
            float uh=Float.parseFloat(height);
            float uw=Float.parseFloat(weight);
            int ag=Integer.parseInt(age);
            enerycalc= (float) (655.1+(9.6*uw)+(1.8*uh)-(4.7*ag));
            DecimalFormat di=new DecimalFormat("0.00");
            energycalclator=di.format(enerycalc);

        }
        else
        {
            int uh=Integer.parseInt(height);
            int uw=Integer.parseInt(weight);
            int ag=Integer.parseInt(age);
            enerycalc= (float) (66+(13.7*uw)+(5*uh)-(6.8*ag));

        }

        DecimalFormat di=new DecimalFormat("0.00");
        energycalclator=di.format(enerycalc);



        float yourenergy= enerycalc;
        if (lan.equals("English")) {

            t2.setText("Your Required Energy: " +energycalclator);

        }
        else
        {
            t2.setText("உங்களுக்கு தேவையான ஆற்றல் "+energycalclator);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youren=Float.valueOf(yourenergy);
                needen=Float.valueOf(need);


                if(yourenergy==needen) {
                    if (lan.equals("English")) {


                        builder.setMessage("You Do to Healthy Eating!! View Caloric Percentage")
                                .setCancelable(false)
                                .setPositiveButton("View", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(new Intent(userviewfooddetails.this, finalanswerdisplay.class));


                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        startActivity(new Intent(userviewfooddetails.this, userviewfooddetails.class));


                                    }

                                });

                        AlertDialog alert = builder.create();
                        alert.setTitle("FoodLogInfo");
                        alert.show();
                    }
                    else {



                            builder.setMessage("நீங்கள் ஆரோக்கியமான உணவையே எடுத்து கொண்டுள்ளீர்கள் !! கலோரிக் சதவீதத்தைக் காண்க")
                                    .setCancelable(false)
                                    .setPositiveButton("ஆம்", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            startActivity(new Intent(userviewfooddetails.this, finalanswerdisplay.class));


                                        }
                                    })
                                    .setNegativeButton("இல்லை", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            startActivity(new Intent(userviewfooddetails.this, userviewfooddetails.class));


                                        }

                                    });

                            AlertDialog alert = builder.create();
                            alert.setTitle("FoodLogInfo");
                            alert.show();

                    }
                }
                else if(needen>youren){
                    if (lan.equals("English")) {

                        builder.setMessage("Your Energy Level is high!! View Caloric Percentage")
                                .setCancelable(false)
                                .setPositiveButton("", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(new Intent(userviewfooddetails.this, finalanswerdisplay.class));


                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        startActivity(new Intent(userviewfooddetails.this, userviewfooddetails.class));


                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("Food Log Info");
                        alert.show();
                    }
                    else
                    {
                        builder.setMessage("நீங்கள் ஆரோக்கியமான உணவுக்கு அதிகமாக எடுத்துக் கொண்டுள்ளீர்கள் !! கலோரிக் சதவீதத்தைக் காண்க")
                                .setCancelable(false)
                                .setPositiveButton("ஆம்", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(new Intent(userviewfooddetails.this, finalanswerdisplay.class));


                                    }
                                })
                                .setNegativeButton("இல்லை", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        startActivity(new Intent(userviewfooddetails.this, userviewfooddetails.class));


                                    }

                                });

                        AlertDialog alert = builder.create();
                        alert.setTitle("Food Log Info");
                        alert.show();

                    }


                }
                else {
                    if (lan.equals("English")) {

                        builder.setMessage("You Energy Level is Low!! View Caloric Percentage")
                                .setCancelable(false)
                                .setPositiveButton("View", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(new Intent(userviewfooddetails.this, finalanswerdisplay.class));


                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        startActivity(new Intent(userviewfooddetails.this, userviewfooddetails.class));


                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("Food Log Info");
                        alert.show();
                    }
                    else
                    {
                        builder.setMessage("நீங்கள் ஆரோக்கியமான உணவுக்கு குறைவாக  எடுத்துக் கொண்டுள்ளீர்கள் !! கலோரிக் சதவீதத்தைக் காண்க")
                                .setCancelable(false)
                                .setPositiveButton("ஆம்", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(new Intent(userviewfooddetails.this, finalanswerdisplay.class));


                                    }
                                })
                                .setNegativeButton("இல்லை", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        startActivity(new Intent(userviewfooddetails.this, userviewfooddetails.class));


                                    }

                                });

                        AlertDialog alert = builder.create();
                        alert.setTitle("Food Log Info");
                        alert.show();

                    }
                }

            }
        });





        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child(noofdays);
        recyclerView = findViewById(R.id.userfoodselection);
        recyclerView.setHasFixedSize(true);
        ProductsRef.child("TotalEnergy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                need= (String) dataSnapshot.child("totenergy").getValue();
                if(lan.equals("English")) {
                    t1.setText("Your  Energy: " + need);
                }
                else {

                    t1.setText("உங்கள் ஆற்றல் " + need);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductsRef.child("Energy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<separatecalculation>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    separatecalculation f = dataSnapshot1.getValue(separatecalculation.class);
                    list.add(f);
                }
                useradapter=new SelectionFoodView(userviewfooddetails.this,list);
                recyclerView.setAdapter(useradapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }
}
