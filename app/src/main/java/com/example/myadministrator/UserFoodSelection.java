package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myadministrator.Fragments.BreakfastFragment;
import com.example.myadministrator.Fragments.DinnerFragment;
import com.example.myadministrator.Fragments.LunchFragment;
import com.example.myadministrator.Model.UserFoodList;
import com.example.myadministrator.ViewHolder.UserFoodViewHolder;
import com.example.myadministrator.listener.QuantityChangeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class UserFoodSelection extends AppCompatActivity {
FloatingActionButton bn;
    DatabaseReference ProductsRef;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserFoodList> list,mylist;
    UserFoodViewHolder useradapter;
    QuantityChangeListener mQuantityChangeListener;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food_selection);
getSupportActionBar().hide();
        bn=findViewById(R.id.fab);
        TabLayout tab=findViewById(R.id.tabs);
        ViewPager viewpager=findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new BreakfastFragment(),"Select A Food");
        viewpager.setAdapter(viewPagerAdapter);
        tab.setupWithViewPager(viewpager);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserFoodSelection.this,Userfoodselecteditemlist.class));
                finish();
            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {

            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fr,String title){
            fragments.add(fr);
            titles.add(title);

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
