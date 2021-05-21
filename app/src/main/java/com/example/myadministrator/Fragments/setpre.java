package com.example.myadministrator.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

import com.example.myadministrator.R;

public class setpre extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new frg()).commit();
    }
    public static class frg extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent intent=new Intent(getApplicationContext(), setpre.class);
        startActivity(intent);
    }
}
