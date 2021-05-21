package com.example.myadministrator.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadministrator.Model.SelectFood;
import com.example.myadministrator.Model.UserFoodList;
import com.example.myadministrator.R;
import com.example.myadministrator.Userfoodselecteditemlist;
import com.example.myadministrator.fooddescription;
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

public class SelectFoodViewHolder extends RecyclerView.Adapter<SelectFoodViewHolder.MyUserViewHolder>{

    Context context;
    int x;
    String rename;
    String userid,noofdays;
    String saveCurrentDate;
    DatabaseReference foodadd;
    DatabaseReference retrivedata;
    FirebaseUser user;
    FirebaseDatabase mdatabase;
    String carbohd,energy,protein,fat,fibre,net;
    DatabaseReference userdetails;
    private float carbo=0,protain=0,enegy=0,fati=0,fibree=0,nett=0;
    ArrayList<SelectFood> userfoodrecipes;
public static int cartcount=0;
    public SelectFoodViewHolder(Context context, ArrayList<SelectFood> userfoodrecipes) {
        this.context = context;
        this.userfoodrecipes = userfoodrecipes;
           }



    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.selectingfooditem,parent,false);
        return new MyUserViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyUserViewHolder holder,final int position) {


        mdatabase=FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user=FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();


        foodadd=FirebaseDatabase.getInstance().getReference().child("Users").child(userid);




        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        noofdays=saveCurrentDate.toString();


        holder.txtRecipeName.setText(userfoodrecipes.get(position).getRecipename());
        Picasso.get().load(userfoodrecipes.get(position).getFoodimage()).into(holder.Food_image);
        holder.txtquantity.setText((userfoodrecipes.get(position).getSizeof()));
        carbohd=userfoodrecipes.get(position).getCarbocall();
        protein=userfoodrecipes.get(position).getProteincall();
        fat=userfoodrecipes.get(position).getFatcall();
        fibre=userfoodrecipes.get(position).getFibrecall();
        net=userfoodrecipes.get(position).getNetcall();
        energy=userfoodrecipes.get(position).getEnergycall();
    holder.txtcount.setText("" + userfoodrecipes.get(position).getQuantity());
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 x=Integer.parseInt((String) holder.txtcount.getText());
                x++;
                String a=String.valueOf(x);
                Context context=v.getContext();
                holder.txtcount.setText(a);
                foodadd.child(noofdays).child("Food").child(userfoodrecipes.get(position).getRecipename()).child("quantity").setValue(a);
                foodadd.child(noofdays).child("Energy").child(userfoodrecipes.get(position).getRecipename()).child("quantity").setValue(a);

                Toast.makeText(context, "update" , Toast.LENGTH_SHORT).show();
                foodadd.child(noofdays).child("Energy").child(userfoodrecipes.get(position).getRecipename()).child("image").setValue(userfoodrecipes.get(position).getFoodimage());
//carbo
                float carbo1=(Float.valueOf(userfoodrecipes.get(position).getCarbocall()))*(Float.valueOf(a));
                carbo=carbo+carbo1;
                String c1=String.valueOf(carbo);
                foodadd.child(noofdays).child("Energy").child(userfoodrecipes.get(position).getRecipename()).child("carbo").setValue(c1);

                float protein1=(Float.valueOf(userfoodrecipes.get(position).getProteincall()))*(Float.valueOf(a));
                protain=protain+protein1;
                String c2=String.valueOf(protain);
                foodadd.child(noofdays).child("Energy").child(userfoodrecipes.get(position).getRecipename()).child("protein").setValue(c2);




                float energy1=(Float.valueOf(userfoodrecipes.get(position).getEnergycall()))*(Float.valueOf(a));
                enegy=enegy+energy1;
                String c3=String.valueOf(enegy);
                foodadd.child(noofdays).child("Energy").child(userfoodrecipes.get(position).getRecipename()).child("energy").setValue(c3);

//fat
                float fat1=(Float.valueOf(userfoodrecipes.get(position).getFatcall()))*(Float.valueOf(a));
                fati=fati+fat1;
                String c4=String.valueOf(fati);
                foodadd.child(noofdays).child("Energy").child(userfoodrecipes.get(position).getRecipename()).child("fat").setValue(c4);


//fibre
                float fibre1=(Float.valueOf((userfoodrecipes.get(position).getFibrecall()))*(Float.valueOf(a)));
                fibree=fibree+fibre1;
                String c5=String.valueOf(fibree);
                foodadd.child(noofdays).child("Energy").child(userfoodrecipes.get(position).getRecipename()).child("fibre").setValue(c5);

//net
                float net1=(Float.valueOf((userfoodrecipes.get(position).getNetcall()))*(Float.valueOf(a)));
                nett=nett+net1;
                String c6=String.valueOf(nett);
                foodadd.child(noofdays).child("Energy").child(userfoodrecipes.get(position).getRecipename()).child("net").setValue(c6);

            }
        });

holder.minus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      x=Integer.parseInt(userfoodrecipes.get(position).getQuantity());
        x--;
        String a=String.valueOf(x);
        Context context=v.getContext();
        holder.txtcount.setText(a);
        foodadd.child(userid).child(noofdays).child("Food").child(userfoodrecipes.get(position).getRecipename()).child("quantity").setValue(a);


    }


});


    }


    @Override
    public int getItemCount() {
        return userfoodrecipes.size();
    }


    public class MyUserViewHolder extends RecyclerView.ViewHolder {
        TextView txtRecipeName,txtcount,txtquantity;
        ImageView Food_image;
        ImageView plus,minus;
        CardView mcard;
        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRecipeName=(TextView)itemView.findViewById(R.id.selectuserfood_name);
            Food_image=(ImageView)itemView.findViewById(R.id.userselectfood_photo);
            plus=(ImageView)itemView.findViewById(R.id.selectaddcount);
            minus=(ImageView)itemView.findViewById(R.id.selectminuscount);
            txtcount=(TextView)itemView.findViewById(R.id.selectcount);
            txtquantity=(TextView)itemView.findViewById(R.id.selectsize);

            mcard=(CardView)itemView.findViewById(R.id.foodcardview);
                      itemView.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                          }
                      });



        }
    }

}
