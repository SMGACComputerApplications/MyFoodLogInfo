package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.HashMap;

public class BMICalculator extends AppCompatActivity implements Preference.OnPreferenceChangeListener{

    EditText uweight,uheight,userage;
    Button bmicalc;
String userid;
    private StorageReference UserprofileRef;
    FirebaseUser user;
    private DatabaseReference UserRef;
    private ProgressDialog loadingBar;
    DatabaseReference userdetails;
    FirebaseDatabase mdatabase;
    String lan,weight;
TextView title;
String msgtamil;
    String gender,bmicalcresult,uage,bmimsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);



        mdatabase=FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();





title=findViewById(R.id.Bmitext);

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        uweight = findViewById(R.id.userweight);
        uheight = findViewById(R.id.userheight);
        userage = findViewById(R.id.userage);
        bmicalc = findViewById(R.id.bmicalc);


        SharedPreferences setting=PreferenceManager.getDefaultSharedPreferences(this);
        lan=setting.getString("Language","");
        Toast.makeText(getApplicationContext(), ""+lan, Toast.LENGTH_SHORT).show();


        if(lan.equals("English"))
        {
                          title.setText("BMI Calculation");
                      uweight.setHint("Enter Your Weight (Kg)");
                      uheight.setHint("Enter your Height(cm)");
                      userage.setHint("Enter your Age");
                      bmicalc.setText("BMI Calculation");

        }
        else {
            title.setText("உடல் நிறை குறியீட்டெண் ");

            uweight.setHint("எடை(கிலோ)");
            uheight.setHint("உயரம்( செ.மீ)");
            userage.setHint("வயது");
            bmicalc.setText("உடல் நிறை குறியீட்டெண் (கிலோ / மீ 2)");
        }




        loadingBar = new ProgressDialog(this);
        bmicalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             womenbmi();


            }
        });


    }

    private void womenbmi(){


String weight=uweight.getText().toString();
String height=uheight.getText().toString();
if(lan.equals("English")) {
    if (TextUtils.isEmpty(weight)) {
        Toast.makeText(this, "Please Enter Your Weight ..", Toast.LENGTH_SHORT).show();
    }
    if (TextUtils.isEmpty(height)) {
        Toast.makeText(this, "Please Enter Your Height ..", Toast.LENGTH_SHORT).show();
    }

    if (TextUtils.isEmpty(uage)) {
        Toast.makeText(this, "Please Enter Your age ..", Toast.LENGTH_SHORT).show();
    }

}
else {
    if (TextUtils.isEmpty(weight)) {
        Toast.makeText(this, " எடை..", Toast.LENGTH_SHORT).show();
    }
    if (TextUtils.isEmpty(height)) {
        Toast.makeText(this, "உயரம்..", Toast.LENGTH_SHORT).show();
    }

    if (TextUtils.isEmpty(uage)) {
        Toast.makeText(this, "வயது ..", Toast.LENGTH_SHORT).show();
    }


}

        float w=Float.parseFloat(weight);
        float h=Float.parseFloat(height);
          float h1=h/100;
           float bmi=w/(h1*h1);
             DecimalFormat df=new DecimalFormat("0.00");
                bmicalcresult=df.format(bmi).toString();
        if(bmi<18.5)
            bmimsg="Under".toString();
        else if((bmi>18.5)&&(bmi<24.9))
            bmimsg="Ideal".toString();
        else if((bmi>25.0)&&(bmi<29.9))
            bmimsg="Over Weight".toString();
        else
            bmimsg="Obese".toString();

uage=userage.getText().toString().trim();
        loadingBar.setTitle("Add Your Bmi Value");
        loadingBar.setMessage(" please wait.....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        HashMap<String,Object> bmivalue=new HashMap<>();
        bmivalue.put("age",uage);
        bmivalue.put("weight",weight);
        bmivalue.put("height",height);
        bmivalue.put("bmiresult",bmimsg);
        bmivalue.put("bmivalue",bmicalcresult);
        UserRef.updateChildren(bmivalue).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    loadingBar.dismiss();
                    Toast.makeText(BMICalculator.this, "BMI Value is added successfully..", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BMICalculator.this,waisthip.class);
                    startActivity(intent);

                }
                else
                {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(BMICalculator.this, "Error: " + message, Toast.LENGTH_LONG).show();
                }



            }
        });


    }



    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }
}

