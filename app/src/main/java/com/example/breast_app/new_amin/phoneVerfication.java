package com.example.breast_app.new_amin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breast_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class phoneVerfication extends AppCompatActivity {
EditText editTextPhone,editTextCode,editTextName,editTextpassword;
FirebaseAuth mAuth;
String codeSent, Code ,name , phone, password ;
boolean isVerified = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verfication);
        editTextCode=findViewById(R.id.verificationCode);
        editTextPhone=findViewById(R.id.phone_number);
        editTextName = findViewById(R.id.userPhoneName);
        editTextpassword = findViewById(R.id.userPhonePassword);
        findViewById(R.id.btSignIn).setEnabled(false);
        mAuth= FirebaseAuth.getInstance();

        findViewById(R.id.getVerificationCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sendVerificationCode();

            }
        });
        findViewById(R.id.btSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            VerifySignInCode();
            }
        });
        findViewById(R.id.userPhoneLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent =new Intent(phoneVerfication.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void VerifySignInCode(){
         Code  = editTextCode.getText().toString();
        if (Code.isEmpty()){
            editTextCode.setError("Code is Required");
            editTextCode.requestFocus();
            return;
        }
        PhoneAuthCredential credential   = PhoneAuthProvider.getCredential(codeSent, Code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        name = editTextName.getText().toString();
        password = editTextpassword.getText().toString();
        phone =editTextPhone.getText().toString();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            mAuth.getCurrentUser().updateProfile(profile);
                            User user1=null;
                            try {

                                user1 = new User(mAuth.getCurrentUser().getUid(),name,phone, Security.encrypt(password),isVerified);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            FirebaseDatabase.getInstance().getReference("User").child(mAuth.getCurrentUser().getUid()).setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        AuthCredential authCredential = EmailAuthProvider.getCredential(phone+"@gmail.com", password);
                                        mAuth.getCurrentUser().linkWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                                                    mAuth.signOut();
                                                    finish();
                                                    Intent intent =new Intent(phoneVerfication.this,MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }else{
                                                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                                    }
                                    else {
                                        Toast.makeText(phoneVerfication.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });




                        } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                            Toast.makeText(getApplicationContext(),"Incorrect Code",Toast.LENGTH_SHORT).show();
                        }
                        }
                    }
                });
    }
    private void sendVerificationCode() {
        phone = editTextPhone.getText().toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        Query query = ref.orderByChild("username").equalTo(phone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Toast.makeText(phoneVerfication.this, "User Exist", Toast.LENGTH_LONG).show();

                } else {
                   createUser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent =s;
            findViewById(R.id.btSignIn).setEnabled(true);

        }
    };
private void createUser(){
    name = editTextName.getText().toString();
    password = editTextpassword.getText().toString();
    findViewById(R.id.getVerificationCode).setEnabled(false);
    findViewById(R.id.btSignIn).setEnabled(false);
    if (name.isEmpty()) {
        editTextName.setError("name is required");
        editTextName.requestFocus();
        findViewById(R.id.getVerificationCode).setEnabled(true);
        return;
    }


    if (password.isEmpty()) {
        editTextpassword.setError("password is required");
        editTextpassword.requestFocus();
        findViewById(R.id.getVerificationCode).setEnabled(true);
        return;
    }


    if (phone.isEmpty()) {
        editTextPhone.setError("Phone Number is Required");
        editTextPhone.requestFocus();
        findViewById(R.id.getVerificationCode).setEnabled(true);
        return;
    }
    if (phone.length() < 10) {
        editTextPhone.setError("Please Enter A Valid Phone Number");
        editTextPhone.requestFocus();
        findViewById(R.id.getVerificationCode).setEnabled(true);
        return;
    }
    Toast.makeText(phoneVerfication.this, "Code will be sent in less than one minute", Toast.LENGTH_LONG).show();
    PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phone,        // Phone number to verify
            60,                 // Timeout duration
            TimeUnit.SECONDS,   // Unit of timeout
            this,               // Activity (for callback binding)
            mCallbacks);        // OnVerificationStateChangedCallbacks
    Timer buttonTimer = new Timer();
    buttonTimer.schedule(new TimerTask() {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    findViewById(R.id.getVerificationCode).setEnabled(true);
                }
            });
        }
    }, 60000);
}
}
