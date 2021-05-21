package com.example.myadministrator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.example.myadministrator.Fragments.BreakfastFragment;
import com.example.myadministrator.Fragments.setpre;
import com.example.myadministrator.Model.SelectFood;
import com.example.myadministrator.ViewHolder.SelectFoodViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Main3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Preference.OnPreferenceChangeListener {

    DatabaseReference ProductsRef;
    CharSequence ch1;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<SelectFood> list;
    Spinner language;
    SelectFoodViewHolder useradapter;
    private LinearLayout linearLayout;
Handler mhandler;
protected String languageapp;

    Context context;
    int x;
   public static String lan;
    String userid, noofdays;
    String saveCurrentDate;
    DatabaseReference foodadd, mainfoodadd;
    DatabaseReference retrivedata,productref2;
    FirebaseUser user;
    FirebaseDatabase mdatabase;
    DatabaseReference userdetails;
    ViewFlipper flipper;
    LinearLayout lo,lo2,lo3;
    Button b1,b2;
    AlertDialog.Builder builder;
    Boolean b=false;
    TextView t6;
    ImageView v1;
    TextView v2;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Food Log Info");
        setSupportActionBar(toolbar);
lo=findViewById(R.id.lin);
        lo2=findViewById(R.id.lin2);
t6=new TextView(this);
        builder=new AlertDialog.Builder(this);
        mdatabase = FirebaseDatabase.getInstance();
        userdetails = mdatabase.getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
language=findViewById(R.id.language);

        b1=findViewById(R.id.nextfood);

this.mhandler=new Handler();
m_runnable.run();




            SharedPreferences setting=PreferenceManager.getDefaultSharedPreferences(this);
            lan=setting.getString("Language","");
            languageapp=lan.toString();

        if(lan.equals("English"))
        {
            lo.setVisibility(LinearLayout.GONE);
            b1.setText("Next");

        }
        else
        {
            lo2.setVisibility(LinearLayout.GONE);
            b1.setText("அடுத்து");

        }



        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);


        SharedPreferences setting2= PreferenceManager.getDefaultSharedPreferences(this);

        String weight=setting2.getString("weight","");



        b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED) {
            b = true;
                    if(lan.equals("English")){
                        Toast.makeText(getApplicationContext(), "Replace withh your own action", Toast.LENGTH_SHORT).show();
                        View view=findViewById(R.id.nextfood);

                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        if(!(weight.equals(""))){
                            Intent dis = new Intent(Main3Activity.this, UserFoodSelection.class);
                            startActivity(dis);


                        }
                        else
                        {
                            builder.setMessage("You Calculate a Bmi Value")
                                    .setCancelable(false)
                                    .setPositiveButton("View", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            startActivity(new Intent(Main3Activity.this,bmivisual.class));


                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            startActivity(new Intent(Main3Activity.this,Main3Activity.class));


                                        }
                                    });
                            AlertDialog alert=builder.create();
                            alert.setTitle("FoodLogInfo");
                            alert.show();

                        }

                    }
                    else{
                        View view=findViewById(R.id.nextfood);


                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        if(!(weight.equals(""))){
                            Intent dis = new Intent(Main3Activity.this, UserSelectTamil.class);
                            startActivity(dis);
                        }
                        else
                        {
                            builder.setMessage("உடல் நிறை குறியீட்டெண் (கிலோ / மீ 2) கணக்கீடு")
                                    .setCancelable(false)
                                    .setPositiveButton("கணக்கீடுக", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            startActivity(new Intent(Main3Activity.this,bmivisual.class));


                                        }
                                    })
                                    .setNegativeButton("வேண்டாம்", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            startActivity(new Intent(Main3Activity.this,Main3Activity.class));


                                        }
                                    });
                            AlertDialog alert=builder.create();
                            alert.setTitle("FoodLogInfo");
                            alert.show();

                        }


                    }




        }
        else {
            b = false;
            if(lan.equals("English")) {
                builder.setMessage("Check Your Internet Connection")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();

                                Intent dis = new Intent(Main3Activity.this, Main3Activity.class);
                                startActivity(dis);


                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("Food Log Info");
                alert.show();
            }
            else
            {
                builder.setMessage("உங்கள் மொபைல் தரவை சரி செய்க")
                        .setCancelable(false)
                        .setPositiveButton("சரி", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();

                                Intent dis = new Intent(Main3Activity.this, Main3Activity.class);
                                startActivity(dis);


                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("Food Log Info");
                alert.show();
            }
        }





    }
});
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    View headerView = navigationView.getHeaderView(0);

if(lan.equals("English")){
    navigationView.inflateMenu(R.menu.activity_main3_drawer);
}
else {
    navigationView.inflateMenu(R.menu.tamil);
}
        navigationView.setNavigationItemSelectedListener(this);

        mdatabase = FirebaseDatabase.getInstance();
        userdetails = mdatabase.getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();

        foodadd = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        noofdays = saveCurrentDate.toString();
        TextView v1 = headerView.findViewById(R.id.textView1);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        recyclerView = findViewById(R.id.selectfood);
        recyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);



        ProductsRef.child(noofdays).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Food")){
                    lo.setVisibility(LinearLayout.GONE);
                    lo2.setVisibility(LinearLayout.GONE);

                    ProductsRef.child(noofdays).child("Food").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            list = new ArrayList<SelectFood>();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                SelectFood f = dataSnapshot1.getValue(SelectFood.class);
                                list.add(f);
                            }
                            useradapter = new SelectFoodViewHolder(Main3Activity.this, list);
                            recyclerView.setAdapter(useradapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Main3Activity.this, "" + databaseError, Toast.LENGTH_SHORT).show();

                        }
                    });



                }
                else
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ImageView im = headerView.findViewById(R.id.imageView1);
        foodadd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String s = (String) dataSnapshot.child("profilepic").getValue();
                Picasso.get().load(s).into(im);
                String t = (String) dataSnapshot.child("useremail").getValue();
                v1.setText(t);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            finish();
            drawer.closeDrawer(GravityCompat.START);

        }
        if(lan.equals("English")) {
            new AlertDialog.Builder(this).setTitle("Exit").setMessage("Are you Sure?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("no", null).show();

        }
        else
        {
            new AlertDialog.Builder(this).setMessage("வெளியேறு?").setPositiveButton("ஆம்", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("இல்லை", null).show();

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


            if (id == R.id.details) {

                startActivity(new Intent(Main3Activity.this, bmivisual.class));
            } else if (id == R.id.setting) {
                startActivity(new Intent(Main3Activity.this,settingsapp.class));

            } else if (id == R.id.searchday) {
                startActivity(new Intent(Main3Activity.this, previousdaydetails.class));

            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void call(){

    }

    @Override
    protected void onStart() {
        super.onStart();



    }



    @Override
    protected void onStop() {
        super.onStop();

    }
    private final  Runnable m_runnable=new Runnable() {
        @Override
        public void run() {
            Main3Activity.this.mhandler.postDelayed(m_runnable,200000);
        }
    };


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        finish();
        Intent intent=new Intent(Main3Activity.this,Main3Activity.class);
        startActivity(intent);

        return true;
    }
}
