package com.example.myadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ntt.customgaugeview.library.GaugeView;

import java.text.DecimalFormat;

public class displaybmivisual extends AppCompatActivity {
    String lan,bmimseg;
TextView disscr;
TextView uheight1,uweight1,uwaist,uhip,ubmi,uwaisthip,rt,bmimsg;
ImageView v1;
    float ratio,enerycalc;
    String energycalclator,waisthipratio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaybmivisual);


        disscr=findViewById(R.id.bmitext1);

        final GaugeView gv=(GaugeView) findViewById(R.id.gauge);
        gv.setShowRangeValues(true);
        gv.setTargetValue(0);
bmimsg=findViewById(R.id.bmimessage1);
        uweight1=findViewById(R.id.bmivalueweight1);
        uheight1=findViewById(R.id.bmivalueheight1);
        uhip=findViewById(R.id.bmihip1);
        uwaist=findViewById(R.id.bmiwaist1);
        ubmi=findViewById(R.id.bmimsg2);

        uwaisthip=findViewById(R.id.bmiratio);
        rt=findViewById(R.id.bmimsg1);
        Intent in=new Intent(this,bmivisual.class);
        Intent ina=getIntent();

        v1=findViewById(R.id.waistdisplay);


        float a = ina.getFloatExtra("bmivalue", 0);
        float b=ina.getFloatExtra("bodyshape",0);
        float c=ina.getFloatExtra("waisthipmsg",0);
         bmimseg=ina.getStringExtra("bmimsg");

            float ratio=ina.getFloatExtra("ratio",0);
        SharedPreferences setting= PreferenceManager.getDefaultSharedPreferences(this);
        lan=setting.getString("Language","");

blink();


        if(lan.equals("English"))
        {
            disscr.setText("BMI Calculation(Kg/m");
        }
        else
        {
           disscr.setText("உடல் நிறை குறியீட்டெண் (கிலோ / மீ 2)");
        }


        CountDownTimer timer=new CountDownTimer(10000,2) {
            @Override
            public void onTick(long millisUntilFinished) {

                gv.setTargetValue(Float.valueOf(a));
                DecimalFormat df=new DecimalFormat("0.00");
                df.format(a);


            }

            @Override
            public void onFinish() {
                gv.setTargetValue(Float.valueOf(a));


            }
        };
        timer.start();


        if(ratio<=0.80){

            v1.setImageResource(R.drawable.pear2);

            if(lan.equals("English"))
            {
                rt.setText("Low: Pear Body Shape");
            }
            else
            {
                rt.setText(" குறைவான: பேரிக்காய் உடல் வடிவம்  ");
            }



        }
        else if( (ratio<0.85)){
            rt.setText("Moderate: Avocado Body Shape");
            v1.setImageResource(R.drawable.pear);

            if(lan.equals("English"))
            {
                rt.setText("Moderate: Avocado Body Shape");
            }
            else
            {
                rt.setText("மிதமான : அவகேடோஉடல் வடிவம்  ");
            }


        }
        else {
            rt.setText("Health Risk: Apple Shape");
            v1.setImageResource(R.drawable.apple);
            if(lan.equals("English"))
            {
                rt.setText("Health Risk: Apple Body Shape");
            }
            else
            {
                rt.setText("ஆபத்துக் கூறுகள்: ஆப்பிள் உடல் வடிவம்  ");
            }


        }


        if(lan.equals("English")){
            if(bmimseg.equals("Under")){
                bmimsg.setText(" Your BMI Value is Under");
            }
            if(bmimseg.equals("Ideal")){
                bmimsg.setText("Your BMI Value Ideal");
            }
            if(bmimsg.equals("Over Weight")){
                bmimsg.setText("Your BMI  value Over Weight");

            }
            if(bmimseg.equals("Obese")){

                bmimsg.setText("Your BMI  value Obese");
            }

        }
        else {
            if(bmimseg.equals("Under")){

                bmimsg.setText("குறைவான  எடை : ஆரோக்கிய குறைவிற்கான வாய்ப்புகள்  நடுநிலை  ");
            }
            if(bmimseg.equals("Ideal")){
                bmimsg.setText("ஆரோக்கியமான எடை :ஆரோக்கிய குறைவிற்கான வாய்ப்புகள்  குறைவு ");
            }
            if(bmimsg.equals("Over Weight")){
                bmimsg.setText("அதிக எடை  :ஆரோக்கிய  குறைவிற்கான  வாய்ப்புகள் அதிகம்  :");

            }
            if(bmimseg.equals("Obese")){

                bmimsg.setText("மிகவும் அதிக எடை: ஆரோக்கிய குறைவிற்கான வாய்ப்புகள் மிகவும் அதிகம்");
            }

        }

        SharedPreferences setting2= PreferenceManager.getDefaultSharedPreferences(this);

        String weight=setting2.getString("weight","");
        String height=setting2.getString("height","");
        String waist=setting2.getString("waist","");
        String hip=setting2.getString("hip","");
        String age=setting2.getString("age","");
        float wa=Float.parseFloat(waist);
        float hi=Float.parseFloat(hip);
        float ratio1 = wa / hi;


        String gen=setting2.getString("gender","");
        if(lan.equals("English"))
        {
            uheight1.setText("Height(cm) : "+height);

            uweight1.setText("Weight (Kg) : "+weight);

            ubmi.setText("BMI Value : "+a);
            uwaist.setText("Waist : "+waist);
            uhip.setText("Hip : "+hip);
            uwaisthip.setText("Waist Hip ratio : "+ratio1);

        }
        else {

            uheight1.setText("உயரம்( செ.மீ)"+weight);

            uweight1.setText("எடை(கிலோ)"+height);
            ubmi.setText("உடல் நிறை குறியீட்டெண் (கிலோ / மீ 2) : "+a);
            uwaist.setText("அடிவயிறு இடுப்பு சுற்றளவு (செ.மீ) :  "+waist);
            uhip.setText("இடுப்பு சுற்றளவு (செ.மீ) : "+hip);
            uwaisthip.setText(" அடிவயிறு இடுப்பு மற்றும் இடுப்பு சுற்றளவு விகிதம் : "+ratio1);


        }



    }
    public void blink()
    {
        final Handler handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timetoblink=1000;
                try {
                    Thread.sleep(timetoblink);
                }
                catch (Exception e)
                {

                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(bmimsg.getVisibility()== View.VISIBLE) {
                            bmimsg.setVisibility(View.VISIBLE);

                        }
                        else
                            bmimsg.setVisibility(View.VISIBLE);
                    }

                });
            }
        }).start();
    }
}
