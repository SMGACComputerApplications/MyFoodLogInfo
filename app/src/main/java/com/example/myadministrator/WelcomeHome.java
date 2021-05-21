package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeHome extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

   CircularImageView imageview;
    Button btnup,upload;
    EditText rname,rqunantity,runit,renergy,rcarbo,rprotein,rfat,rfibre,rnet;

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private static final String DB_IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    Spinner sp;
       String ad;
    private StorageReference storageReference;
    private static final String DATA_DIRECTORY = "FoodEntry";

    private  DatabaseReference databaseReference;
    private Uri uri;
    private Uri contentURI;
    ProgressDialog progressDialog;

ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_home);
requestMultiplePermissions();

        imageview=findViewById(R.id.circleview);
        btnup=findViewById(R.id.upload);
        sp=findViewById(R.id.unit);
        rname=findViewById(R.id.RecipeName);
        rqunantity=findViewById(R.id.Quantity);
        renergy=findViewById(R.id.Energy);
        rcarbo=findViewById(R.id.Carbohyd);
        rprotein=findViewById(R.id.Protein);
        rfat=findViewById(R.id.Fat);
        rfibre=findViewById(R.id.Fibre);
        rnet=findViewById(R.id.NetCarb);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.unitsSpin,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp.setAdapter(adapter);



        sp.setOnItemSelectedListener(this);
        storageReference=FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference(DATA_DIRECTORY);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showPictureDialog();
            }
        });
        upload=findViewById(R.id.save);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImageFileToFirebaseStorage();


            }

        });

    }

    public void UploadImageFileToFirebaseStorage(){
        String tname=rname.getText().toString().trim();
        String tquantity=rqunantity.getText().toString().trim();
        String tenergy=renergy.getText().toString().trim();
        String tcarbo=rcarbo.getText().toString().trim();
        String tprotein=rprotein.getText().toString().trim();
        String tfat=rfat.getText().toString().trim();
        String tfibre=rfibre.getText().toString().trim();
        String tnet=rnet.getText().toString().trim();


        if ( contentURI!= null) {

            // Setting progressDi
            // Showing progressDialog.
            StorageReference storageReference2nd = storageReference.child(IMAGE_DIRECTORY + System.currentTimeMillis() + "." + GetFileExtension(contentURI));

            storageReference2nd.putFile(contentURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                    @SuppressWarnings("VisibleForTests")
                    ImageUpLoadInfo imageUpLoadInfo;
                    imageUpLoadInfo = new ImageUpLoadInfo(tname, storageReference.getDownloadUrl().toString(),ad.toString(),tquantity,tenergy,tcarbo,tprotein,tfat,tfibre,tnet);
                    String ImageUploadId = databaseReference.push().getKey();

                    // Adding image upload id s child element into databaseReference.
                    databaseReference.child(ImageUploadId).setValue(imageUpLoadInfo);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    // Showing exception erro message.
                    Toast.makeText(WelcomeHome.this, e.getMessage(), Toast.LENGTH_LONG).show();


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }

        else
        {
            Toast.makeText(WelcomeHome.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }


    }

    private void requestMultiplePermissions() {

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                }

                // check for permanent denial of any permission
                if (report.isAnyPermissionPermanentlyDenied()) {
                    // show alert dialog navigating to Settings
                    //openSettingsDialog();
                }


            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();




            }

        private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public String saveImage(Bitmap MyBitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        MyBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";


    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                 contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(WelcomeHome.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(WelcomeHome.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(WelcomeHome.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

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
                   }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();
        ad=item;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();


        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    }
