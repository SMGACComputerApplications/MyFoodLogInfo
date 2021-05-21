package com.example.myadministrator;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.*;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {
EditText emailid,pwd;
Button btnsignup,btnlogin;
private FirebaseAuth mfirebase;
TextView nextuse;
ProgressBar p2;
String userid;
private FirebaseAuth.AuthStateListener mauthstate;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mfirebase=FirebaseAuth.getInstance();
        FirebaseUser currentuser=mfirebase.getCurrentUser();
        emailid=findViewById(R.id.emailid);
        pwd=findViewById(R.id.password);
        nextuse=findViewById(R.id.nextuser);
        nextuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,UserRegistration.class));


            }
        });

        btnlogin=findViewById(R.id.login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();
                 String password =pwd.getText().toString();
                 if(TextUtils.isEmpty(email)){
                     emailid.setError("Please Enter the Email address");
                     emailid.requestFocus();
                 }
                if(TextUtils.isEmpty(password)){
                    pwd.setError("Please Enter the Email address");
                    pwd.requestFocus();
                }
                if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(Main2Activity.this, "Enter the Fields", Toast.LENGTH_SHORT).show();
                }
                if(!(email.isEmpty() && password.isEmpty())){

                    mfirebase.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(Main2Activity.this, "createUserWithEmail:onComplete:"+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            if(!task.isSuccessful()){
                                Toast.makeText(Main2Activity.this, "Authentication failed."+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                            else
                                startActivity(new Intent(Main2Activity.this, Main2Activity.class));
                            finish();


                        }
                    });

                }

                }
        });
        mauthstate=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mfireuser=mfirebase.getCurrentUser();
                if(mfireuser!=null){
                    Toast.makeText(Main2Activity.this, "You are Logged", Toast.LENGTH_SHORT).show();

                    userid=mfirebase.getCurrentUser().getUid();

                    Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                    startActivity(intent);
                    finish();



                }

                else
                    Toast.makeText(Main2Activity.this, "Please Login Here", Toast.LENGTH_SHORT).show();
            }
        };



    }

    @Override
    protected void onStart() {
        super.onStart();
        mfirebase.addAuthStateListener(mauthstate);
    }
}
