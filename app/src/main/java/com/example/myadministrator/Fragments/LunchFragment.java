package com.example.myadministrator.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myadministrator.Model.UserFoodList;
import com.example.myadministrator.R;
import com.example.myadministrator.ViewHolder.UserFoodViewHolder;
import com.example.myadministrator.listener.QuantityChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.myadministrator.R.layout.fragment_breakfast;
import static com.example.myadministrator.R.layout.homecontent;


public class LunchFragment extends Fragment {
    DatabaseReference ProductsRef;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserFoodList> list;
    UserFoodViewHolder useradapter;
    QuantityChangeListener mQuantityChangeListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(homecontent,container,false);

        return view;
    }

}
