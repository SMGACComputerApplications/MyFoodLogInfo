package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

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
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class finalanswerdisplay extends AppCompatActivity {

    String saveCurrentDate,noofdays,userid;
    DatabaseReference foodadd;
    String cartlist;
    DatabaseReference retrivedata;
    FirebaseUser user;
    TextView carbo,protein,fati;
    TextView t1,t2,t3,t4;
    FirebaseDatabase mdatabase;
    DatabaseReference userdetails;
PieChartView pieChartView,pieChartView1;
String carbo2,protein2,fat2;
String lan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalanswerdisplay);
getSupportActionBar().hide();
        carbo=findViewById(R.id.carbocall);
        protein=findViewById(R.id.proteincall);
        fati=findViewById(R.id.fatcal);

        mdatabase=FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user=FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();


t1=findViewById(R.id.n1);
t2=findViewById(R.id.n2);
t3=findViewById(R.id.n3);
t4=findViewById(R.id.n4);


        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        noofdays=saveCurrentDate.toString();
        foodadd=FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child(noofdays);




        SharedPreferences setting= PreferenceManager.getDefaultSharedPreferences(this);
        lan=setting.getString("Language","");
if(lan.equalsIgnoreCase("தமிழ்"))
{
    t1.setText("கலோரி சதவீதம்");
    t2.setText("மாவுச்சத்து");
    t3.setText("புரதச்சத்து");
    t4.setText("கொழுப்பு");
}
else
{
t1.setText("Caloric Percentage");
t2.setText("Carbo");
t3.setText("Protein");
t4.setText("Fat");
}


        pieChartView=findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, Color.BLUE).setLabel("10-15%"));
        pieData.add(new SliceValue(25, Color.RED).setLabel("20-30%"));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("50-60%"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);

        pieChartView.setPieChartData(pieChartData);






        foodadd.child("TotalEnergy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String carb= (String) dataSnapshot.child("totcarbo").getValue();
                String pro= (String) dataSnapshot.child("totprotein").getValue();
                String fat= (String) dataSnapshot.child("totfat").getValue();
                float ca=Float.valueOf(carb)*4;
                float pr=Float.valueOf(pro)*4;
                float fa=Float.valueOf(fat)*9;
                float ene=ca+pr+fa;
                float carb1=(ca/ene)*100;
                float protein1=(pr/ene)*100;
                float fat1=(fa/ene)*100;
                DecimalFormat die=new DecimalFormat("0.00");
                carbo2=die.format(carb1);
                protein2=die.format(protein1);
                fat2=die.format(fat1);

                String a=String.valueOf(carbo2+"%");
                String b=String.valueOf(protein2+"%");
                String c=String.valueOf(fat2+"%");

                carbo.setText(a);
                protein.setText(b);

                fati.setText(c);

                String ab= (String) protein.getText();
                String abc= (String) fati.getText();
                String abcd= (String) carbo.getText();


                pieChartView1=findViewById(R.id.chart1);
                List<SliceValue> pieData1 = new ArrayList<>();
                pieData1.add(new SliceValue(15, Color.BLUE).setLabel(""+ab));
                pieData1.add(new SliceValue(25, Color.RED).setLabel(""+abc));
                pieData1.add(new SliceValue(60, Color.MAGENTA).setLabel(""+abcd));
                PieChartData pieChartData1 = new PieChartData(pieData1);
                pieChartData1.setHasLabels(true).setValueLabelTextSize(10);

                pieChartView1.setPieChartData(pieChartData1);




            };

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
