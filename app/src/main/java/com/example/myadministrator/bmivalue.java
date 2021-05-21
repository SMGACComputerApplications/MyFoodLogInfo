
        package com.example.myadministrator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.google.firebase.database.annotations.Nullable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class bmivalue extends PreferenceFragment
{
    public static Context contextOfApplication;
    String lan;
    public Context context;
    public static SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.bmidisplay);

        EditTextPreference height= (EditTextPreference) findPreference("height");
         EditTextPreference weight= (EditTextPreference) findPreference("weight");
       EditTextPreference age= (EditTextPreference) findPreference("age");
        ListPreference gender= (ListPreference) findPreference("gender");
EditTextPreference waist= (EditTextPreference) findPreference("waist");
EditTextPreference hip= (EditTextPreference) findPreference("hip");
ListPreference work= (ListPreference) findPreference("work");
ListPreference goal= (ListPreference) findPreference("goal");
EditTextPreference ed= (EditTextPreference) findPreference("energy");
        SharedPreferences setting= PreferenceManager.getDefaultSharedPreferences(getActivity());
        lan=setting.getString("Language","");
        String he=height.getText();
        String we=weight.getText();
        String ag=age.getText();
        String wa=waist.getText();
        String hi=hip.getText();
        String wo=work.getValue();
        String go=goal.getValue();
        String ge=gender.getValue();

        Toast.makeText(getActivity(),"language"+lan,Toast.LENGTH_LONG).show();
if(lan.equals("English")){
    height.setTitle("Height");
    height.setSummary("Your Height "+height.getText()+" cm");
    height.setDialogMessage("Your Height");
    height.setNegativeButtonText("No");
    height.setPositiveButtonText("Yes");
    weight.setTitle("Weight");
    weight.setSummary("Your Weight"+weight.getText()+" kg");
    weight.setDialogMessage("Your Weight");
    weight.setNegativeButtonText("No");
    weight.setPositiveButtonText("Yes");
    age.setTitle("Age");
    age.setSummary("Your Age "+age.getText());
    age.setDialogMessage("Your Age");
    age.setNegativeButtonText("No");
    age.setPositiveButtonText("Yes");
    CharSequence[] genders={"ஆண்","பெண்"};
    gender.setTitle("Gender ");
    gender.setSummary("Your Gender"+gender.getValue().toString());
    gender.setEntryValues(R.array.gendertam);
    gender.setEntries(R.array.gendereng);
    gender.setNegativeButtonText("No");
    waist.setTitle("Waist ");
    waist.setSummary("Your Waist size "+waist.getText()+" cm");
    waist.setDialogMessage("Your Waist ");
    waist.setNegativeButtonText("No");
    waist.setPositiveButtonText("Yes");
    hip.setTitle("Hip ");
    hip.setSummary("Your Hip Size "+hip.getText()+" cm");
    hip.setDialogMessage("Your Hip ");
    hip.setNegativeButtonText("No");
    hip.setPositiveButtonText("Yes");
    work.setEntryValues(R.array.workeng);
    work.setTitle("Your Work");
    work.setSummary("Your Work"+work.getValue());
    work.setEntries(R.array.workeng);
    work.setNegativeButtonText("No");
    goal.setEntryValues(R.array.goaleng);
    goal.setTitle("Select Your Work");
    goal.setTitle("Select Your Work  :  "+goal.getValue());
    goal.setEntries(R.array.goaleng);
    goal.setNegativeButtonText("No");
}
else
{
    height.setTitle("உயரம்(செ.மீ)");
    height.setSummary("உங்களின் உயரம் "+height.getText()+" செ.மீ");
    height.setDialogMessage("உங்களின் உயரம்");
    height.setNegativeButtonText("இல்லை");
    height.setPositiveButtonText("ஆம்");

    weight.setTitle("எடை(கிலோ)");
    weight.setSummary("உங்களின் எடை "+weight.getText()+" கிலோ");
    weight.setDialogMessage("உங்களின் எடை");
    weight.setNegativeButtonText("இல்லை");
    weight.setPositiveButtonText("ஆம்");
    age.setTitle("வயது");
    age.setSummary("உங்களின் வயது "+age.getText());
    age.setDialogMessage("உங்களின் வயது");
    age.setNegativeButtonText("இல்லை");
    age.setPositiveButtonText("ஆம்");
CharSequence[] genders={"ஆண்","பெண்"};
    gender.setTitle("பாலினம் ");
    gender.setSummary("உங்களின் பாலினம் "+gender.getValue().toString());
    gender.setEntryValues(R.array.gendertam);
    gender.setEntries(R.array.gendertam);
    gender.setNegativeButtonText("இல்லை");
    waist.setTitle("அடிவயிறு இடுப்பு சுற்றளவு (செ.மீ) ");
    waist.setSummary("உங்களின் அடிவயிறு இடுப்பு சுற்றளவு (செ.மீ) "+waist.getText()+" செ.மீ");
    waist.setDialogMessage("உங்களின் அடிவயிறு இடுப்பு சுற்றளவு (செ.மீ) ");
    waist.setNegativeButtonText("இல்லை");
    waist.setPositiveButtonText("ஆம்");
    hip.setTitle("இடுப்பு சுற்றளவு (செ.மீ) ");
    hip.setSummary("உங்களின் இடுப்பு சுற்றளவு (செ.மீ) "+hip.getText()+" செ.மீ");
    hip.setDialogMessage("உங்களின் இடுப்பு சுற்றளவு (செ.மீ) ");
    hip.setNegativeButtonText("இல்லை");
    hip.setPositiveButtonText("ஆம்");
    work.setEntryValues(R.array.worktam);
    work.setTitle("உங்கள் வேலையைத் தேர்ந்தெடுக்கவும்");
    work.setSummary("உங்கள் வேலை"+work.getValue());
    work.setEntries(R.array.worktam);
    work.setNegativeButtonText("இல்லை");
    goal.setEntryValues(R.array.goaltam);
    goal.setTitle("உங்கள் இலக்கைத் தேர்ந்தெடுக்கவும்");
    goal.setTitle("உங்கள் இலக்கு  :  "+goal.getValue());
    goal.setEntries(R.array.goaltam);
    goal.setNegativeButtonText("இல்லை");

}

gender.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Locale locale;
        if(lan.equals("English")){
            locale=new Locale("Select your Gender");
        }
        else {
            locale=new Locale("உங்களின் பாலினம்");
        }

        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getActivity().getApplicationContext().getResources().updateConfiguration(config,null);
getFragmentManager().beginTransaction().replace(R.id.fragment_container1,new bmivalue()).commit();
        return true;
    }
});

    }
}
