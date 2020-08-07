package com.example.breast_app.new_amin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breast_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class updateUserInfo extends AppCompatActivity {
    EditText editName,editCurrentPassword,editNewPassword;
    Button updateName,updatePassword,breturn;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        editName = findViewById(R.id.update_name);
        editCurrentPassword = findViewById(R.id.currentPassword);
        editNewPassword = findViewById(R.id.update_password);
        updateName = findViewById(R.id.btupdateName);
        updatePassword=findViewById(R.id.updatepassword);
        breturn=findViewById(R.id.btReturn);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("User");
        userId = mAuth.getCurrentUser().getUid();
        breturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(updateUserInfo.this,Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        updateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                if (name.isEmpty()){
                    editName.setError("Name is required");
                    return;
                }
                updateName(userId,name);
            }
        });
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final String currentPassword = editCurrentPassword.getText().toString().trim();
                 final String newPassword = editNewPassword.getText().toString().trim();

                if (currentPassword.isEmpty()){
                    editCurrentPassword.setError("password is required");
                    editCurrentPassword.requestFocus();
                    return;
                }
                if (newPassword.isEmpty()){
                    editNewPassword.setError("password is required");
                    editNewPassword.requestFocus();
                    return;
                }
                if (currentPassword.length()<8){
                    editCurrentPassword.setError("Minimum length of password should be 8");
                    editCurrentPassword.requestFocus();
                    return;
                }
                if (newPassword.length()<8){
                    editNewPassword.setError("Minimum length of password should be 8");
                    editNewPassword.requestFocus();
                    return;
                }
                updatePassword(userId,currentPassword,newPassword);

            }
        });
    }
    private void updateName(String id,String name){
        DatabaseReference databaseReference = mDatabase.child(id);

        databaseReference.child("displayname").setValue(name);
        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        mAuth.getCurrentUser().updateProfile(profile);
        Toast.makeText(this,"User successfully updated",Toast.LENGTH_LONG).show();

    }
    private void updatePassword(String id,final String currentPassword,final String newPassword){
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        final DatabaseReference databaseReference = mDatabase.child(id);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        Query query = ref.orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    User users = user.getValue(User.class);

                    try {
                        if (users.password.equals(Security.encrypt(currentPassword))) {

                            firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        try {
                                            databaseReference.child("password").setValue(Security.encrypt(newPassword));
                                            Toast.makeText(updateUserInfo.this,"password updated",Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            Intent intent =new Intent(updateUserInfo.this,MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        Toast.makeText(updateUserInfo.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(updateUserInfo.this, "Password is wrong", Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });


    }
}
