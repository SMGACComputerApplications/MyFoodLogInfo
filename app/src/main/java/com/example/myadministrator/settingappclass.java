package com.example.myadministrator;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

public class settingappclass extends PreferenceFragment {
    protected String lanh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        ListPreference listPreference=(ListPreference)findPreference("Language");
        listPreference.setDefaultValue("English");
        CharSequence currtext=listPreference.getEntry();
        String currvalue=listPreference.getValue();
        lanh=currvalue.toString();

        Preference.OnPreferenceChangeListener onPreferenceChangeListener=new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                Preference.OnPreferenceChangeListener listener= (Preference.OnPreferenceChangeListener) getActivity();
                listener.onPreferenceChange(preference,newValue);
                return true;
            }
        };

        listPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);
        if(currvalue.equalsIgnoreCase("தமிழ்"))
        {


            listPreference.setTitle("மொழி");
            listPreference.setSummary(" உங்கள் மொழியை தேர்ந்தெடுங்கள்");
            listPreference.setNegativeButtonText("வெளியேறு");




        }
        else {
            listPreference.setTitle("Language");

            listPreference.setSummary("select Your Language");
            listPreference.getDependency();
        }
    }

}

