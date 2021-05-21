package com.example.myadministrator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class bmipre extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        EditTextPreference height=(EditTextPreference)findPreference("height");
        EditTextPreference weight=(EditTextPreference)findPreference("weight");
        Preference.OnPreferenceChangeListener onPreferenceChangeListener=new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                Preference.OnPreferenceChangeListener listener= (Preference.OnPreferenceChangeListener) getActivity();
                listener.onPreferenceChange(preference,newValue);
                return true;
            }
        };




    }
}
