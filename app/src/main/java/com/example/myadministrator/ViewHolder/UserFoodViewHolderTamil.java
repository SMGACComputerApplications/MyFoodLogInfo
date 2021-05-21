package com.example.myadministrator.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadministrator.Model.UserFoodList;
import com.example.myadministrator.R;
import com.example.myadministrator.listener.QuantityChangeListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class UserFoodViewHolderTamil extends RecyclerView.Adapter<UserFoodViewHolderTamil.MyUserViewHolder> implements Filterable {

    Context context;
    int x;
    String xc;
    Float xc1;
    String rename;
    String userid,noofdays;
    Object fcarbo;
    String fenergy;
    String fprotein;
    String ffat;
    String ffibre;
    String fnet,ftot;
    float ffcarbo,ffenergy,ffprotein,fffat,ffnet,fffibre,fftot;
    float ac=0,ae=0,an=0,af=0,afi=0,ap=0;
    QuantityChangeListener mQcl;
    String saveCurrentDate;
    DatabaseReference foodadd;
String cartlist;
    DatabaseReference retrivedata;
    FirebaseUser user;
    FirebaseDatabase mdatabase;
    Activity a;
    String carbohd,energy,protein,fat,fibre,net;
    DatabaseReference userdetails;
    ArrayList<UserFoodList> userfoodrecipes;
    ArrayList<UserFoodList> userrecipesfull;
public static int cartcount=0;
    public UserFoodViewHolderTamil( Context context, ArrayList<UserFoodList> userfoodrecipes) {
        this.context = context;
        this.userfoodrecipes = userfoodrecipes;
    }




    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.userfoodview,parent,false);
        return new MyUserViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyUserViewHolder holder,final int position) {
        mdatabase=FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user=FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();


        foodadd=FirebaseDatabase.getInstance().getReference().child("Users");

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        noofdays=saveCurrentDate.toString();

        holder.txtRecipeName.setText(userfoodrecipes.get(position).getRecipename());
        rename=userfoodrecipes.get(position).getRecipename();
        String g=userfoodrecipes.get(position).getImage();
        Picasso.get().load(userfoodrecipes.get(position).getImage()).into(holder.Food_image);
        holder.txtquantity.setText((userfoodrecipes.get(position).getQuantity())+(userfoodrecipes.get(position).getQuantitysize()));

        holder.txtcount.setText("" + userfoodrecipes.get(position).getCount());
        carbohd=(userfoodrecipes.get(position).getCarbo()).toString();

        energy=(userfoodrecipes.get(position).getEnergy()).toString();
        protein=(userfoodrecipes.get(position).getProtein()).toString();
        fat=(userfoodrecipes.get(position).getFat()).toString();
        fibre=(userfoodrecipes.get(position).getFibre()).toString();
        net=(userfoodrecipes.get(position).getNetCarb());

        retrivedata=FirebaseDatabase.getInstance().getReference().child(userid).child(noofdays).child("Food");

        //mcardview
       //minus

                if (userfoodrecipes.contains(userfoodrecipes.get(position))){
                    holder.mcard.setCardBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
                }
else {
    holder.mcard.setCardBackgroundColor(ContextCompat.getColor(context,R.color.white));
                }
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtcount.setText("Removed Item");
                foodadd.child(userid).child(noofdays).child("Food").child(userfoodrecipes.get(position).getRecipename()).removeValue();
                Toast.makeText(context,"Remove a Item", Toast.LENGTH_SHORT).show();
                foodadd.child(userid).child(noofdays).child("Food").child(userfoodrecipes.get(position).getRecipename()).removeValue();
                foodadd.child(noofdays).child("Energy").child(userfoodrecipes.get(position).getRecipename()).removeValue();
            }

                //calculation

        });

        //plus

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtcount.setText("Added Item ");
               String a=String.valueOf(currentDate.format(calendar.getTime()));
               String  b=a.replaceAll(",$","");

               b=a.replaceAll("\\s+","");
                HashMap<String,Object> fo1=new HashMap<>();
                fo1.put("dayde",noofdays);

                foodadd.child(userid).child("Days").child(b).updateChildren(fo1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
                HashMap<String, Object> food = new HashMap<>();
                food.put("recipename", userfoodrecipes.get(position).getRecipename());
                food.put("foodimage", userfoodrecipes.get(position).getImage());
                food.put("carbocall",(userfoodrecipes.get(position).getCarbo()));
                food.put("energycall",(userfoodrecipes.get(position).getEnergy()));
                food.put("proteincall",userfoodrecipes.get(position).getProtein());
                food.put("quantity","0");
                food.put("fatcall",(userfoodrecipes.get(position).getFat()));
                food.put("fibrecall",(userfoodrecipes.get(position).getFibre()));
                food.put("netcall",(userfoodrecipes.get(position).getNetCarb()));
                food.put("sizeof",(userfoodrecipes.get(position).getQuantity())+(userfoodrecipes.get(position).getQuantitysize()));
                foodadd.child(userid).child(noofdays).child("Food").child(userfoodrecipes.get(position).getRecipename()).updateChildren(food).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"Add a Item", Toast.LENGTH_SHORT).show();

                    }
                });

                          }




        });


    }
    public void call(){
        if((foodadd.child(userid).child(noofdays)).child("Daywise").child("Energy").equals((foodadd.child(userid).child(noofdays)))){
            Toast.makeText(context, "already success successfully..", Toast.LENGTH_SHORT).show();

        }
        else {
            create();
            update();
        }

    }

    public void update(){
        foodadd.child(userid).child(noofdays).child("Energy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fcarbo= dataSnapshot.child("carbo").getValue().toString();
                fenergy= dataSnapshot.child("energy").getValue().toString();
                fprotein= dataSnapshot.child("protein").getValue().toString();
                ffat=dataSnapshot.child("fat").getValue().toString();
                ffibre= dataSnapshot.child("fibre").getValue().toString();
                fnet=dataSnapshot.child("net").getValue().toString();
                ftot=dataSnapshot.child("tot").getValue().toString();
                //float
                Toast.makeText(context, "added successfully.."+fcarbo, Toast.LENGTH_SHORT).show();





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

public void create(){

   String sac="0";
   String sae="0";
    String san="0";
    String saf="0";
    String safi="0";
    String sap="0";
    String tot="0";


    HashMap<String,Object> food=new HashMap<>();
    food.put("carbo",sac);
    food.put("energy",sae);
    food.put("fat",saf);
    food.put("protein",sap);
    food.put("fibre",safi);
    food.put("net",san);
    food.put("tot",tot);

    foodadd.child(userid).child(noofdays).child("Energy").updateChildren(food).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

            Toast.makeText(context, "added successfully..", Toast.LENGTH_SHORT).show();

        }
    });


}

    @Override
    public int getItemCount() {
        return userfoodrecipes.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UserFoodList> filteredlist=new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filteredlist.addAll(userrecipesfull);

            }
            else {
                String filterpattern=constraint.toString().toLowerCase();
                if(filterpattern.isEmpty()){
                    userrecipesfull=userfoodrecipes;
                }
                else {
                    for (UserFoodList item : userrecipesfull) {
                        if (item.getRecipename().toLowerCase().contains(filterpattern) ){
                            filteredlist.add(item);
                        }

                    }
                }
            }
            FilterResults results=new FilterResults();
            results.count=filteredlist.size();
            results.values=filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            userfoodrecipes.clear();
            userfoodrecipes.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    public class MyUserViewHolder extends RecyclerView.ViewHolder {
        TextView txtRecipeName,txtcount,txtquantity;
        ImageView Food_image;
        ImageView plus,minus;
        CardView mcard;
        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRecipeName=(TextView)itemView.findViewById(R.id.userfood_name);
            Food_image=(ImageView)itemView.findViewById(R.id.userfood_photo);
            plus=(ImageView)itemView.findViewById(R.id.addcount);
            minus=(ImageView)itemView.findViewById(R.id.minuscount);
            txtcount=(TextView)itemView.findViewById(R.id.count);
            txtquantity=(TextView)itemView.findViewById(R.id.size);

            mcard=(CardView)itemView.findViewById(R.id.foodcardview);
                      itemView.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                          }
                      });



        }
    }

}
