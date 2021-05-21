package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;

import com.example.myadministrator.Model.UserFoodList;
import com.example.myadministrator.ViewHolder.UserFoodViewHolder;
import com.example.myadministrator.ViewHolder.UserFoodViewHolderTamil;
import com.example.myadministrator.listener.QuantityChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.ImmutableSortedMap;

import java.util.ArrayList;

import static com.example.myadministrator.R.string.app_name;
import static java.security.AccessController.getContext;

public class UserSelectTamil extends AppCompatActivity {

    DatabaseReference ProductsRef;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserFoodList> list, mylist;
    UserFoodViewHolderTamil useradapter;
    QuantityChangeListener mQuantityChangeListener;
    androidx.appcompat.widget.SearchView searchViewtamil;

    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select_tamil);
        getSupportActionBar().hide();


        ProductsRef = FirebaseDatabase.getInstance().getReference().child("MainFoodTamil");
        searchViewtamil = findViewById(R.id.searchfoodtamil);
        searchViewtamil.setQueryHint("Search Your Foods Here");
        recyclerView = findViewById(R.id.breakfastfoodlisttamil);
        recyclerView.setHasFixedSize(true);


        mGridLayoutManager = new GridLayoutManager(this, 2);
        b1 = findViewById(R.id.nextfood);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSelectTamil.this, Userfoodselecteditemlist.class));
                finish();

            }
        });
        recyclerView.setLayoutManager(mGridLayoutManager);
        ProductsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<UserFoodList>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    UserFoodList f = dataSnapshot1.getValue(UserFoodList.class);
                    list.add(f);
                }
                useradapter = new UserFoodViewHolderTamil(UserSelectTamil.this,list);
                recyclerView.setAdapter(useradapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void setListener(QuantityChangeListener listener) {
        this.mQuantityChangeListener=listener;
    }

    public void search(String str){
        ArrayList<UserFoodList> mylist=new ArrayList<>();
        for(UserFoodList object:list){
            if(object.getRecipename().toLowerCase().contains(str.toLowerCase())){
                mylist.add(object);

            }

            UserFoodViewHolderTamil adapter=new UserFoodViewHolderTamil(UserSelectTamil.this,mylist);
            recyclerView.setAdapter(adapter);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        if(searchViewtamil!=null)
        {
            searchViewtamil.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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
}
