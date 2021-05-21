package com.example.myadministrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class UserRegistration extends AppCompatActivity {


    String saveCurrentDate, saveCurrentTime,userjoin;
    CircularImageView profileimageview;
    private Uri ImageUri;
    private int GALLERY = 1, CAMERA = 2;
EditText usname,uspass,ueemail;
String useremail,userpass,username,gender,downloadImageUrl;
RadioButton rmale,rfemale;
Button registerbtn;
    private ProgressDialog loadingBar;

    private DatabaseReference Userref;
    private StorageReference UserProfileRef;
    FirebaseAuth ufirebase;
    private static final int GalleryPick = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
              requestMultiplePermissions();
        profileimageview=findViewById(R.id.circleprofileview);
        usname=findViewById(R.id.username);
        uspass=findViewById(R.id.userpassword);
        ueemail=findViewById(R.id.useremailid);
        rmale=findViewById(R.id.radiomale);
        rfemale=findViewById(R.id.radiofemale);
        loadingBar = new ProgressDialog(this);
        ufirebase=FirebaseAuth.getInstance();
        Userref= FirebaseDatabase.getInstance().getReference().child("Users");
        UserProfileRef= FirebaseStorage.getInstance().getReference().child("Users");
        registerbtn=findViewById(R.id.register);
        registerbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                ValidateUserData();
            }
        });
        profileimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            profileimageview.setImageURI(ImageUri);
        }


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

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
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

    private void ValidateUserData() {
        username=usname.getText().toString().trim();
        useremail=ueemail.getText().toString().trim();
        userpass=uspass.getText().toString().trim();
        gender="";
        if(rmale.isChecked())
            gender="Male";
        else
            gender="Female";

        if (ImageUri == null)
        {
            Toast.makeText(this, "Profile Pic Image is Mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please write Your Name ..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(useremail))
        {
            Toast.makeText(this, "Please write Email Id ..", Toast.LENGTH_SHORT).show();
        }


        else if (TextUtils.isEmpty(userpass))
        {
            Toast.makeText(this, "Please write Password ..", Toast.LENGTH_SHORT).show();
        }

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        userjoin = saveCurrentDate + saveCurrentTime.toString().trim();


        ufirebase.createUserWithEmailAndPassword(useremail,userpass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserRegistration.this, "Authentication Successfully."+task.getException(), Toast.LENGTH_SHORT).show();
                    loadingBar.setTitle("Add New User");
                    loadingBar.setMessage("Dear User, please wait while we are adding you..");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                    final StorageReference filePath = UserProfileRef.child(ImageUri.getLastPathSegment() + username+ ".jpg");

                    final UploadTask uploadTask = filePath.putFile(ImageUri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String message = e.toString();
                            Toast.makeText(UserRegistration.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UserRegistration.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
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


                                        HashMap<String,Object> userMap=new HashMap<>();
                                        userMap.put("username", username);
                                        userMap.put("useremail",useremail);
                                        userMap.put("userrpassword",userpass);
                                        userMap.put("gender",gender);
                                        userMap.put("profilepic",downloadImageUrl);
                                        userMap.put("userjoin",userjoin);
                                        userMap.put("Language","English");
                                        Userref.child(ufirebase.getCurrentUser().getUid()).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    loadingBar.dismiss();
                                                    Toast.makeText(UserRegistration.this, "Registered successfully..", Toast.LENGTH_SHORT).show();

                                                }
                                                else
                                                {
                                                    loadingBar.dismiss();
                                                    String message = task.getException().toString();
                                                    Toast.makeText(UserRegistration.this, "Error: " + message, Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });



                                    }

                                }
                            });
                        }
                    });

                    startActivity(new Intent(UserRegistration.this, Main2Activity.class));
                    finish();



                }

                else
                    Toast.makeText(UserRegistration.this, "Authentication failed."+task.getException(), Toast.LENGTH_SHORT).show();

            }
        });



    }


}
