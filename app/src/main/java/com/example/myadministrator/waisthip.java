package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;

import com.example.myadministrator.Model.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.HashMap;

public class waisthip extends AppCompatActivity {
    private DatabaseReference UserRef;
    EditText userwaist1,userhip1;
    String userid;
    String waistratio,waisthipresult,waisthipmsg,bodyshape;
    Button btnwaist;
    String gender,weight,height,age,menenergy,womenenergy;
    private ProgressDialog loadingBar;
TextView t1;
    FirebaseUser user;
    DatabaseReference userdetails;
    FirebaseDatabase mdatabase;

float ratio,enerycalc;
    String energycalclator,waisthipratio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waisthip);


        mdatabase=FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();


        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        userwaist1=findViewById(R.id.userwaist);
        userhip1=findViewById(R.id.userhip);
        loadingBar = new ProgressDialog(this);
        btnwaist=findViewById(R.id.waisthipcalc);
        t1=findViewById(R.id.waisthiptext);

        btnwaist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               UserRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     gender= (String) dataSnapshot.child("gender").getValue();
                     weight= (String) dataSnapshot.child("weight").getValue();
                     height= (String) dataSnapshot.child("height").getValue();
                     age= (String) dataSnapshot.child("age").getValue();
                     Toast.makeText(getApplicationContext(),""+gender,Toast.LENGTH_LONG).show();

                     if(gender.equals("Female")){
                         womenenergy();

                     }
                     else
                         menenergy();

                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });



            }


        });


    }
public void womenenergy()
{
    String waist=userwaist1.getText().toString();
    String hip=userhip1.getText().toString();
    if (TextUtils.isEmpty(waist))
    {
        Toast.makeText(this, "Please Enter Your Waist ..", Toast.LENGTH_SHORT).show();
    }
    if (TextUtils.isEmpty(hip))
    {
        Toast.makeText(this, "Please Enter Your Hip ..", Toast.LENGTH_SHORT).show();
    }

    float w=Float.parseFloat(waist);
    float h=Float.parseFloat(hip);
    ratio=w/h;
    DecimalFormat die=new DecimalFormat("0.00");
    waisthipratio=die.format(ratio);


    if(ratio>=0.80) {
        waisthipmsg = "Low".toString();
        bodyshape = "Pear".toString();

    }
    else if((ratio<=0.81)&&(ratio>=0.85)){
    waisthipmsg="Moderate".toString();
    bodyshape="Avocade".toString();

}
            else{
    waisthipmsg="High".toString();
    bodyshape="Apple".toString();
}

            float uh=Float.parseFloat(height);
            float uw=Float.parseFloat(weight);
            int a=Integer.parseInt(age);
            enerycalc= (float) (655.1+(9.6*uw)+(1.8*uh)-(4.7*a));
    DecimalFormat di=new DecimalFormat("0.00");
    energycalclator=di.format(enerycalc);

    loadingBar.setTitle("Add Your Energy Value");
    loadingBar.setMessage(" please wait.....");
    loadingBar.setCanceledOnTouchOutside(false);
    loadingBar.show();

            HashMap<String,Object> energy=new HashMap<>();
            energy.put("waist",waist);
            energy.put("hip",hip);
            energy.put("waisthipsize",waisthipratio);
            energy.put("healthrisk",waisthipmsg);
            energy.put("bodyshape",bodyshape);
            energy.put("energybmr",energycalclator);
            UserRef.updateChildren(energy).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                        Toast.makeText(waisthip.this, "Energy Value is added successfully..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(waisthip.this,Finalquestion.class);
                        startActivity(intent);
                        finish();


                    }
                    else
                    {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(waisthip.this, "Error: " + message, Toast.LENGTH_LONG).show();
                    }


                }
            });


}
public void menenergy(){

    String waist=userwaist1.getText().toString();
    String hip=userhip1.getText().toString();
    if (TextUtils.isEmpty(waist))
    {
        Toast.makeText(this, "Please Enter Your Waist ..", Toast.LENGTH_SHORT).show();
    }
    if (TextUtils.isEmpty(hip))
    {
        Toast.makeText(this, "Please Enter Your Hip ..", Toast.LENGTH_SHORT).show();
    }

    float w=Float.parseFloat(waist);
    float h=Float.parseFloat(hip);
    ratio=w/h;
    if(ratio>=0.95) {
        waisthipmsg = "Low".toString().trim();
        bodyshape = "Pear".toString().trim();

    }
    else if((ratio<=0.96)&&(ratio>=1.0)){
        waisthipmsg="Moderate".toString().trim();
        bodyshape="Avocade".toString().trim();

    }
    else{
        waisthipmsg="High".toString();
        bodyshape="Apple".toString();
    }
    int uh=Integer.parseInt(height);
    int uw=Integer.parseInt(weight);
    int a=Integer.parseInt(age);
    enerycalc= (float) (66+(13.7*uw)+(5*uh)-(6.8*a));
    DecimalFormat di=new DecimalFormat("0.00");
     energycalclator=di.format(enerycalc);
    HashMap<String,Object> energy=new HashMap<>();
    energy.put("waist",waist);
    energy.put("hip",hip);
    energy.put("waisthipsize",ratio);
    energy.put("healthrisk",waisthipmsg);
    energy.put("bodyshape",bodyshape);
    energy.put("energybmr",energycalclator);

    UserRef.updateChildren(energy).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

            if (task.isSuccessful()) {
                loadingBar.dismiss();
                Toast.makeText(waisthip.this, "Energy Value is added successfully..", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences=getSharedPreferences("mykey",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("userid",userid);
                editor.apply();
                Intent intent = new Intent(waisthip.this,Finalquestion.class);
                startActivity(intent);
                finish();


            }
            else
            {
                loadingBar.dismiss();
                String message = task.getException().toString();
                Toast.makeText(waisthip.this, "Error: " + message, Toast.LENGTH_LONG).show();
            }


        }
    });

}

}


