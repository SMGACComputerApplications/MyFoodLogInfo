package com.example.myadministrator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

import java.util.prefs.Preferences;

public class setfragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("Language")){
            Preference exercise=findPreference(key);
            exercise.setTitle("மொழி");

            exercise.setSummary(" உங்கள் மொழியை தேர்ந்தெடுங்கள்");

        }
        else {
            Preference exercise=findPreference(key);
            exercise.setTitle("Language");

            exercise.setSummary("select Your Language");

        }


    }
}
