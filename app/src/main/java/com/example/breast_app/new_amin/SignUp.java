package com.example.breast_app.new_amin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breast_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
EditText editTextEmailAddress,editTextPassword,editTextPhone,editTextName;
    private FirebaseAuth mAuth;
    boolean isVerified =false;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextEmailAddress =findViewById(R.id.etEmail_Address);
        editTextPassword = findViewById(R.id.etPassword);
        editTextName = findViewById(R.id.etName);
        findViewById(R.id.bRegister).setOnClickListener(this);
        findViewById(R.id.btLogin).setOnClickListener(this);
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
    }
private void registerUser(){
        String email = editTextEmailAddress.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
    final     String name = editTextName.getText().toString().trim();

if (email.isEmpty()){
    editTextEmailAddress.setError("username is required");
    editTextEmailAddress.requestFocus();
    return;
}
    if (password.isEmpty()){
        editTextPassword.setError("password is required");
        editTextPassword.requestFocus();
        return;
    }

    if (name.isEmpty()){
        editTextName.setError("name is required");
        editTextName.requestFocus();
        return;
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        editTextEmailAddress.setError("Please enter a valid email");
        editTextEmailAddress.requestFocus();
        return;
    }
    if (password.length()<8){
        editTextPassword.setError("Minimum length of password should be 8");
        editTextPassword.requestFocus();
        return;
    }

    progressBar.setVisibility(View.VISIBLE);



    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            progressBar.setVisibility(View.GONE);
            if(task.isSuccessful()){
                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();
                mAuth.getCurrentUser().updateProfile(profile);
                isVerified =mAuth.getCurrentUser().isEmailVerified();
                User user1=null;
                try {
                    user1 = new User(mAuth.getCurrentUser().getUid(),name,mAuth.getCurrentUser().getEmail(), Security.encrypt(password),isVerified);


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }

                FirebaseDatabase.getInstance().getReference("User").child(mAuth.getCurrentUser().getUid()).setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignUp.this,"Verification of email sent",Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    finish();
                                    Intent intent =new Intent(SignUp.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });



                        }
                        else {
                            Toast.makeText(SignUp.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }else {
                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(getApplicationContext(),"you are already registered",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        }
    });
}
    @Override
    public void onClick(View v) {

switch (v.getId()){
    case R.id.bRegister:
        registerUser();
        break;
    case R.id.btLogin:
        finish();
        startActivity(new Intent(this,MainActivity.class));
        break;
}
    }

}
