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

public class DinnerFragment extends Fragment {
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
        View view=inflater.inflate(fragment_breakfast,container,false);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("MainFood");

        recyclerView = view.findViewById(R.id.breakfastfoodlist);
        recyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(mGridLayoutManager);
        ProductsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list=new ArrayList<UserFoodList>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    UserFoodList f=dataSnapshot1.getValue(UserFoodList.class);
                    list.add(f);
                }
                useradapter=new UserFoodViewHolder(getActivity(),getContext(),list,mQuantityChangeListener);
                recyclerView.setAdapter(useradapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
