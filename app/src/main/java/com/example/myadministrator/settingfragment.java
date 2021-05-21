package com.example.myadministrator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.R;

public class settingfragment extends PreferenceFragment {

    public static Context contextOfApplication;
    String lan;
    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context applicationContext = settingfragment.getContextOfApplication();

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        lan=SP.getString("Language","");
        Toast.makeText(getContextOfApplication(), ""+lan, Toast.LENGTH_SHORT).show();

    }
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
}