package com.example.myadministrator.ViewHolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.example.myadministrator.Model.separatecalculation;
import com.example.myadministrator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SelectionFoodView extends RecyclerView.Adapter<SelectionFoodView.MyUserViewHolder>{

    Context context;
    int x;
    String rename,lan;
    String userid,noofdays;
    String saveCurrentDate;
    DatabaseReference foodadd,mainfoodadd;
    DatabaseReference retrivedata;
    FirebaseUser user;
    FirebaseDatabase mdatabase;
    String carbohd,energy,protein,fat,fibre,net;
    DatabaseReference userdetails;
   private float carbo2=0,protein2=0,energy2=0,fat2=0,fibre2=0,net2=0;
    ArrayList<separatecalculation> userfoodrecipes;
public static int cartcount=0;
    public SelectionFoodView(Context context, ArrayList<separatecalculation> userfoodrecipes) {
        this.context = context;
        this.userfoodrecipes = userfoodrecipes;
           }



    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.selectionfoodview,parent,false);
        return new MyUserViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyUserViewHolder holder,final int position) {



        mdatabase=FirebaseDatabase.getInstance();
        userdetails=mdatabase.getReference().child("Users");
        user=FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();



        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        noofdays=saveCurrentDate.toString();
mainfoodadd=FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        foodadd=FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child(noofdays);

        Picasso.get().load(userfoodrecipes.get(position).getImage()).into(holder.Food_image);

        SharedPreferences setting= PreferenceManager.getDefaultSharedPreferences(context);
        lan=setting.getString("Language","");
        if(lan.equalsIgnoreCase("தமிழ்"))
        {
            holder.qno.setText("அளவு : " + userfoodrecipes.get(position).getQuantity());
            holder.c1.setText("கார்போஹைட்ரேட்டுகள் : " + userfoodrecipes.get(position).getCarbo());
            holder.pr1.setText("புரதச்சத்து : " + userfoodrecipes.get(position).getProtein());
            holder.fa1.setText("கொழுப்பு : " + userfoodrecipes.get(position).getFat());
            holder.en1.setText("ஆற்றல் : " + userfoodrecipes.get(position).getEnergy());
            holder.fi1.setText("நார்சத்து : " + userfoodrecipes.get(position).getFibre());
            holder.n1.setText("நெட் கார்ப் : " + userfoodrecipes.get(position).getNet());


        }
        else {
            holder.qno.setText("Quantity: " + userfoodrecipes.get(position).getQuantity());
            holder.c1.setText("Carbohyd: " + userfoodrecipes.get(position).getCarbo());
            holder.pr1.setText("Protein: " + userfoodrecipes.get(position).getProtein());
            holder.fa1.setText("Fat: " + userfoodrecipes.get(position).getFat());
            holder.en1.setText("Energy: " + userfoodrecipes.get(position).getEnergy());
            holder.fi1.setText("Fibre: " + userfoodrecipes.get(position).getFibre());
            holder.n1.setText("NetCarb: " + userfoodrecipes.get(position).getNet());
        }
        energy=userfoodrecipes.get(position).getEnergy();
        float carbo1=(Float.valueOf(userfoodrecipes.get(position).getCarbo()));
        carbo2=carbo2+carbo1;
        float protein1=(Float.valueOf(userfoodrecipes.get(position).getProtein()));
        protein2=protein2+protein1;
        float energy1=(Float.valueOf(userfoodrecipes.get(position).getEnergy()));
        energy2=energy2+energy1;
        float fat1=Float.valueOf(userfoodrecipes.get(position).getFat());
        fat2=fat2+fat1;
        float fibre1=Float.valueOf(userfoodrecipes.get(position).getFibre());
        fibre2=fibre2+fibre1;
        float net1=Float.valueOf(userfoodrecipes.get(position).getNet());
        net2=net2+net1;
        String c1=String.valueOf(carbo2);
        String c2=String.valueOf(protein2);
        String c3=String.valueOf(energy2);
        String c4=String.valueOf(fat2);
        String c5=String.valueOf(fibre2);
        String c6=String.valueOf(net2);
        HashMap<String,Object> tot=new HashMap<>();
        tot.put("totcarbo",c1);
        tot.put("totprotein",c2);
        tot.put("totenergy",c3);
        tot.put("totfat",c4);
        tot.put("totfibre",c5);
        tot.put("totnet",c6);
        foodadd.child("TotalEnergy").updateChildren(tot).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


            }
        });


    }



    @Override
    public int getItemCount() {
        return userfoodrecipes.size();
    }


    public class MyUserViewHolder extends RecyclerView.ViewHolder {
        TextView qno,c1,pr1,en1,fa1,fi1,n1;
        ImageView Food_image;

        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            Food_image=(ImageView) itemView.findViewById(R.id.circlefoodview1);
            qno=(TextView)itemView.findViewById(R.id.qno1);
            c1=(TextView) itemView.findViewById(R.id.c1);
            pr1=(TextView) itemView.findViewById(R.id.pr1);
            en1=(TextView)itemView.findViewById(R.id.en1);
            fa1=(TextView) itemView.findViewById(R.id.fa1);
            fi1=(TextView) itemView.findViewById(R.id.fi1);
            n1=(TextView) itemView.findViewById(R.id.n1);

                      itemView.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                          }
                      });



        }
    }

}
