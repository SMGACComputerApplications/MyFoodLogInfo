package com.example.myadministrator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class TamilFragment extends Fragment {
    DatabaseReference ProductsRef;
    private GridLayoutManager mGridLayoutManager;
    private  RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserFoodList> list,mylist;
    UserFoodViewHolder useradapter;
    QuantityChangeListener mQuantityChangeListener;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(fragment_breakfast,container,false);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("MainFoodTamil");
       searchView=view.findViewById(R.id.searchfood);
searchView.setQueryHint("Search Your Foods Here");
        recyclerView = view.findViewById(R.id.breakfastfoodlist);
        recyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        Bundle args = getArguments();

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
        return view;



    }


    // TODO: Rename method, update argument and hook method into UI event
    public void setListener(QuantityChangeListener listener) {
        this.mQuantityChangeListener=listener;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(searchView!=null)
        {
            searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }

    }
    public void search(String str){
        ArrayList<UserFoodList> mylist=new ArrayList<>();
        for(UserFoodList object:list){
            if(object.getRecipename().toLowerCase().contains(str.toLowerCase())){
                mylist.add(object);

            }

            UserFoodViewHolder adapter=new UserFoodViewHolder(getActivity(),getContext(),mylist,mQuantityChangeListener);

            recyclerView.setAdapter(adapter);
        }
         }


}
