package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Signup extends AppCompatActivity implements View.OnClickListener {
    EditText emailid,password;
    Button signup;
    TextView tnlogin;
    FirebaseAuth mfirebase;
    ProgressBar p1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mfirebase=FirebaseAuth.getInstance();
        p1=findViewById(R.id.progressBar);
        emailid=findViewById(R.id.emailid);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


    }

    @Override
    public void onClick(View v) {
        String email = emailid.getText().toString();
        String pass = password.getText().toString();
        if (email.isEmpty())
        {
            emailid.setError("Please Enter the Your Email");
            emailid.requestFocus();


        }
        else if(pass.isEmpty())
        {
            password.setError("Please Enter the Password");
            password.requestFocus();

        }
        else if(email.isEmpty() && pass.isEmpty()){
            Toast.makeText(this, "Enter Fields", Toast.LENGTH_SHORT).show();
        }

        else if(!(email.isEmpty() && pass.isEmpty())){
            p1.setVisibility(View.VISIBLE);

            mfirebase.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(Signup.this, "createUserWithEmail:onComplete:"+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                       if(!task.isSuccessful()){
                           Toast.makeText(Signup.this, "Authentication failed."+task.getException(), Toast.LENGTH_SHORT).show();
                       }
                       else
                           startActivity(new Intent(Signup.this, Main2Activity.class));
                       finish();

                }
            });

        }

        }

    @Override
    protected void onResume() {
        super.onResume();
        p1.setVisibility(View.GONE);
    }
}

