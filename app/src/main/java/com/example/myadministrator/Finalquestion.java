package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

public class Finalquestion extends AppCompatActivity {
    RadioButton loseweight,maintainweight,gainweight,secondary,moderate,heavy;
    Button finalque;
    String goal1,work,userid;
    private DatabaseReference UserRef;
    private ProgressDialog loadingBar;
String energyrequire;
String e;
RadioGroup g1,g2;
RadioButton b1,b2;
double neededenergy;
    FirebaseUser user;
    DatabaseReference userdetails;
    FirebaseDatabase mdatabase;

private String var,a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalquestion);
        mdatabase=FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        loseweight=findViewById(R.id.loseweight);
        maintainweight=findViewById(R.id.maintainweight);
        gainweight=findViewById(R.id.gainweight);
        secondary=findViewById(R.id.sedentary);
        moderate=findViewById(R.id.moderateworker);
        loadingBar = new ProgressDialog(this);
        heavy=findViewById(R.id.heavyworker);
        finalque=findViewById(R.id.finalquetion);


        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                energyrequire= (String) dataSnapshot.child("energybmr").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        finalque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(loseweight.isChecked()){

                    goal1="LoseWeight";
                }
                if(maintainweight.isChecked()){
                    goal1="MaintainWeight";

                }
                else {
                    goal1="GainWeight";
                }
                float needed=Float.valueOf(energyrequire);


                if(secondary.isChecked()){
                    work="SedentaryWorker".toString();

                    neededenergy= 1.53*needed;
                    DecimalFormat df=new DecimalFormat("0.00");
                    e=df.format(neededenergy);

                }
                if(moderate.isChecked()){
                    work="Moderate".toString();
                    neededenergy= 1.8*needed;
                    DecimalFormat df=new DecimalFormat("0.00");
                    e=df.format(neededenergy);

                }
                if(heavy.isChecked()){
                    work="HeavyWorker".toString();
                    neededenergy= 2.3*needed;
                    DecimalFormat df=new DecimalFormat("0.00");
                    e=df.format(neededenergy);

                }

                String s=String.valueOf(neededenergy);
                HashMap<String,Object> result=new HashMap<>();
                result.put("goal",goal1);
                result.put("work",work);

                result.put("requiredenergy",e);
                UserRef.updateChildren(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(Finalquestion.this, "Needed Enerrgy Value is added successfully..", Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(Finalquestion.this, "Error: " + message, Toast.LENGTH_LONG).show();
                        }

                    }
                });
                finish();
                Intent intent = new Intent(Finalquestion.this,Main3Activity.class);
                startActivity(intent);



            }

        });

    }


}
