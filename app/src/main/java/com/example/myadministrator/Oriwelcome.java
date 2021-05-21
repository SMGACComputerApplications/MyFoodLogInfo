package com.example.myadministrator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;


public class Oriwelcome extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

CircularImageView imgview;
StorageReference storageReference;
Uri FilePathUri;
DatabaseReference databaseReference;
int Image_Request_Code=7;
ProgressDialog progressDialog;
String ad;
    Button btnup,upload;
    EditText rname,rqunantity,runit,renergy,rcarbo,rprotein,rfat,rfibre,rnet;

Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oriwelcome);
        storageReference= FirebaseStorage.getInstance().getReference("FoodEntry");
        databaseReference= FirebaseDatabase.getInstance().getReference("FoodEntry");
        imgview=findViewById(R.id.circleview);
        btnup=findViewById(R.id.upload);
        sp=findViewById(R.id.unit);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.unitsSpin,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        progressDialog = new ProgressDialog(Oriwelcome.this);
        upload=findViewById(R.id.save);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUpload();
            }
        });
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
    public void ImageUpload()
    {
        if (FilePathUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
        storageReference2.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String tname=rname.getText().toString().trim();
                String tquantity=rqunantity.getText().toString().trim();
                String tenergy=renergy.getText().toString().trim();
                String tcarbo=rcarbo.getText().toString().trim();
                String tprotein=rprotein.getText().toString().trim();
                String tfat=rfat.getText().toString().trim();
                String tfibre=rfibre.getText().toString().trim();
                String tnet=rnet.getText().toString().trim();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                @SuppressWarnings("VisibleForTests")

ImageUpLoadInfo imageupload=new ImageUpLoadInfo(tcarbo,tenergy,tfat,tfibre,tnet,tprotein,tquantity,ad,tname,taskSnapshot.getUploadSessionUri().toString());
                String ImageUploadId = databaseReference.push().getKey();
                databaseReference.push().getKey();
                databaseReference.child(ImageUploadId).setValue(imageupload);
            }
        });
        }
        else
        {
            Toast.makeText(Oriwelcome.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();
        ad=item.toString().trim();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
