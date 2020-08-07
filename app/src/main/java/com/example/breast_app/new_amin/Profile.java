package com.example.breast_app.new_amin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.breast_app.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class Profile extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 100;
    Button delete;
    ImageView imageView;
    TextView editText;
    Uri uriProfileImage;
    ProgressBar progressBar;
    String profileImageUrl;
    FirebaseAuth mAuth;
    Button bReturn;
    Button bEdit;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference mDatabase;
    String userType;
    TextView textViewType ;
    Button bExamination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userType = intent.getStringExtra(MainActivity.UserType);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("User");
        setContentView(R.layout.activity_profile);
        editText= findViewById(R.id.displayName);
        textViewType = findViewById(R.id.textType);
        textViewType.setText(userType);
        delete=findViewById(R.id.bDeleteUser);

        imageView=findViewById(R.id.image);
        progressBar=findViewById(R.id.progressbar);
        bReturn = findViewById(R.id.btProfileReturn);
        bEdit = findViewById(R.id.btEdit);






        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        bReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Profile.this, com.example.breast_app.MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Profile.this,updateUserInfo.class);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
        loadUserInformation();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showUpdateDialog();

            }
        });

    }
    private void showUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_user,null);
        builder.setView(dialogView);
        final Button btnYes = dialogView.findViewById(R.id.yes);
        final Button btnNo = dialogView.findViewById(R.id.no);
        builder.setTitle("Are you sure to delete your account ");
        final AlertDialog alertDialog =builder.create();
        alertDialog.show();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void deleteUser() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String id  = mAuth.getCurrentUser().getUid();


       // mGoogleSignInClient.signOut();
        DatabaseReference drUser = FirebaseDatabase.getInstance().getReference("User").child(id);
        drUser.removeValue();
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mGoogleSignInClient.signOut();
                    Toast.makeText(Profile.this,"account deleted",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(Profile.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Profile.this,"error",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }
    private void loadUserInformation() {
  final FirebaseUser user = mAuth.getCurrentUser();

    if (user!=null){
    if (user.getPhotoUrl()!=null){
        Glide.with(this).load(user.getPhotoUrl().toString()).into(imageView);
    }
    if (user.getDisplayName()!=null) {
        editText.setText("Hi, " +user.getDisplayName());
    }

    }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE  && resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
      uriProfileImage =  data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                imageView.setImageBitmap(bitmap);
                uploadImageToFireBaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
private void uploadImageToFireBaseStorage(){
    final StorageReference profileImageRef =
            FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");
if (uriProfileImage!=null){
    progressBar.setVisibility(View.VISIBLE);
    profileImageRef.putFile(uriProfileImage)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    profileImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            profileImageUrl = task.getResult().toString();
                            FirebaseUser user= mAuth.getCurrentUser();
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(profileImageUrl))
                                    .build();
                            user.updateProfile(profile);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
        progressBar.setVisibility(View.GONE);
            Toast.makeText(Profile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    });

    }}
    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"), CHOOSE_IMAGE);
    }


}
