package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.SimpleTimeZone;

public class MainHome extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int GalleryPick = 1;
    private Uri ImageUri;

       private String productRandomKey, downloadImageUrl,saveCurrentDate, saveCurrentTime;
       private StorageReference ProductImagesRef;
       private DatabaseReference ProductsRef;
       private ProgressDialog loadingBar;



    CircularImageView imageview;
    Button btnup,upload,btnsave;
    EditText rname,rqunantity,runit,renergy,rcarbo,rprotein,rfat,rfibre,rnet,rcategory,rqs;
    Spinner sp;
    String ad;
String tname,tquantity,tenergy,tcarbo,tfat,tfibre,tprotein,tnet,tcate,tqs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
          ProductImagesRef= FirebaseStorage.getInstance().getReference().child("MainFood");
          ProductsRef= FirebaseDatabase.getInstance().getReference().child("MainFood");
        imageview=findViewById(R.id.circleview);
        rname=findViewById(R.id.RecipeName);
        rqunantity=findViewById(R.id.Quantity);
        renergy=findViewById(R.id.Energy);
        rcarbo=findViewById(R.id.Carbohyd);
        rprotein=findViewById(R.id.Protein);
        rfat=findViewById(R.id.Fat);
        rfibre=findViewById(R.id.Fibre);
        rqs=findViewById(R.id.Quanitysize);
        rnet=findViewById(R.id.NetCarb);
        rcategory=findViewById(R.id.CategoryName);
        btnsave=findViewById(R.id.save);
        loadingBar = new ProgressDialog(this);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();

            }
        });

        btnup=findViewById(R.id.upload);
        sp=findViewById(R.id.unit);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.unitsSpin,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();

            }
        });


    }
    private void OpenGallery() {

        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();
        ad=item.toString().trim();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            imageview.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData() {
        tname=rname.getText().toString().trim();
        tquantity=rqunantity.getText().toString().trim();
        tenergy=renergy.getText().toString().trim();
        tcarbo=rcarbo.getText().toString().trim();
        tprotein=rprotein.getText().toString().trim();
        tfat=rfat.getText().toString().trim();
        tfibre=rfibre.getText().toString().trim();
        tnet=rnet.getText().toString().trim();
        tcate=rcategory.getText().toString().trim();
        tqs=rqs.getText().toString().trim();

        if (ImageUri == null)
        {
            Toast.makeText(this, "Food Image is Mandatory", Toast.LENGTH_SHORT).show();
            }
        else if (TextUtils.isEmpty(tcate))
        {
            Toast.makeText(this, "Please write Category Name ..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tqs))
        {
            Toast.makeText(this, "Please write QuantitySize ..", Toast.LENGTH_SHORT).show();
        }


        else if (TextUtils.isEmpty(tname))
        {
            Toast.makeText(this, "Please write Food Name ..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tquantity))
        {
            Toast.makeText(this, "Please write Food Quantity...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tenergy))
        {
            Toast.makeText(this, "Please write Energy ...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tcarbo))
        {
            Toast.makeText(this, "Please write Carbohydrate ...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tprotein))
        {
            Toast.makeText(this, "Please write Protein ...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(tfat))
        {
            Toast.makeText(this, "Please write Fat ...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(tfibre))
        {
            Toast.makeText(this, "Please write Fibre ...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tnet))
        {
            Toast.makeText(this, "Please write Net Carb ...", Toast.LENGTH_SHORT).show();
        }


        else
        {
            StoreProductInformation();
        }


    }
    private void StoreProductInformation() {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
       saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
       saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(MainHome.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainHome.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString().trim();

                            Toast.makeText(MainHome.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }

                    }
                });
            }
        });



    }
    private void SaveProductInfoToDatabase() {

        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("image", downloadImageUrl);
        productMap.put("recipename",tname);
        productMap.put("quantity",tquantity);
        productMap.put("quantitysizeno",ad);
        productMap.put("quantitysize",tqs);
        productMap.put("energy",tenergy);
        productMap.put("carbo",tcarbo);
        productMap.put("protein",tprotein);
        productMap.put("fat",tfat);
        productMap.put("fibre",tfibre);
        productMap.put("netCarb",tnet);
        ProductsRef.child(tname).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadingBar.dismiss();
                    Toast.makeText(MainHome.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(MainHome.this, "Error: " + message, Toast.LENGTH_LONG).show();
                                   }

            }
        });




    }


        @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)

        {
            case R.id.view:
                startActivity(new Intent(MainHome.this,ViewFoodDetails.class));
               }
        return super.onOptionsItemSelected(item);

    }
}
