package com.example.myadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class bmivisual extends AppCompatActivity implements Preference.OnPreferenceChangeListener {
String lan;
String bmimsg;
    public static float ratio;
    public static float enerycalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bmivisual);
        FloatingActionButton bmi = findViewById(R.id.fab1);

        if(findViewById(R.id.fragment_container1)!=null){
            if(savedInstanceState!=      null)
                return;
            getFragmentManager().beginTransaction().add(R.id.fragment_container1,new bmivalue()).commit();

            SharedPreferences setting2= PreferenceManager.getDefaultSharedPreferences(this);

            bmi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String weight=setting2.getString("weight","");
                    String height=setting2.getString("height","");
                    String waist=setting2.getString("waist","");
                    String hip=setting2.getString("hip","");
                    String age=setting2.getString("age","");
String gen=setting2.getString("gender","");

                    float w=Float.parseFloat(weight);
                    float h=Float.parseFloat(height);
                    float h1=h/100;
                    float bmical=w/(h1*h1);
                    DecimalFormat df=new DecimalFormat("0.00");
                    df.format(bmical);


                    if(bmical<18.5)
                        bmimsg="Under".toString();
                    else if((bmical>18.5)&&(bmical<24.9))
                        bmimsg="Ideal".toString();
                    else if((bmical>25.0)&&(bmical<29.9))
                        bmimsg="Over Weight".toString();
                    else
                        bmimsg="Obese".toString();

                    Toast.makeText(getApplicationContext(),"bmi"+bmimsg,Toast.LENGTH_LONG).show();

                    float wa=Float.parseFloat(waist);
                    float hi=Float.parseFloat(hip);
                    float ratio = w / h;

                    String waisthipmsg;
                    String bodyshape;
                    if(ratio>=0.80) {
                        waisthipmsg = "Low".toString();
                        bodyshape = "Pear".toString();

                    }
                    else if((ratio<=0.81)&&(ratio>=0.85)){
                        waisthipmsg="Moderate".toString();
                        bodyshape="Avocade".toString();

                    }
                    else{
                        waisthipmsg="High".toString();
                        bodyshape="Apple".toString();
                    }


                    String energycalclator;
                    if(gen.equals("Male")|| gen.equals("ஆண்")){
                        float uh=Float.parseFloat(height);
                        float uw=Float.parseFloat(weight);
                        int ag=Integer.parseInt(age);
                        enerycalc= (float) (655.1+(9.6*uw)+(1.8*uh)-(4.7*ag));
                        DecimalFormat di=new DecimalFormat("0.00");
                        energycalclator=di.format(enerycalc);

                    }
                    else
                    {
                        int uh=Integer.parseInt(height);
                        int uw=Integer.parseInt(weight);
                        int ag=Integer.parseInt(age);
                        enerycalc= (float) (66+(13.7*uw)+(5*uh)-(6.8*ag));
                        DecimalFormat di=new DecimalFormat("0.00");
                        energycalclator=di.format(enerycalc);

                    }



                    Intent in=new Intent(v.getContext(),displaybmivisual.class);
                    in.putExtra("bmivalue",bmical);
                    in.putExtra("bmimsg",bmimsg);
                    in.putExtra("waisthipmsg",waisthipmsg);
                    in.putExtra("bodyshape",bodyshape);
                    in.putExtra("energy",enerycalc);
                    in.putExtra("ratio",ratio);
                    startActivity(in);


                }
            });

        }




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        Intent intent=new Intent(bmivisual.this,Main3Activity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        finish();
        Intent intent=new Intent(bmivisual.this,bmivisual.class);
        startActivity(intent);

        return true;

    }
}
