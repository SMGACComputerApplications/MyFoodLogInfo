package com.example.myadministrator.ViewHolder;

import android.os.Bundle;

import com.example.myadministrator.Main3Activity;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Main3Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
