package com.example.myadministrator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class settingsapp extends AppCompatActivity implements Preference.OnPreferenceChangeListener {

    String lan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsapp);

        if(findViewById(R.id.fragment_container)!=null){
            if(savedInstanceState!=      null)
                return;
            getFragmentManager().beginTransaction().add(R.id.fragment_container,new settingappclass()).commit();

            SharedPreferences setting=PreferenceManager.getDefaultSharedPreferences(this);
            lan=setting.getString("Language","");
            Toast.makeText(getApplicationContext(), ""+lan, Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent=new Intent(settingsapp.this,Main3Activity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        finish();
        Intent intent=new Intent(settingsapp.this,settingsapp.class);
        startActivity(intent);

        return true;


    }
}
