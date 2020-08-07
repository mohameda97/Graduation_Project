package com.example.breast_app.new_amin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breast_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUserName, editTextPassword,verificationCodeReset;
    FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    ProgressBar progressBar;
    TextView passwordTextView;
    Button btUpdate;
    String codeSent, Code,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        findViewById(R.id.btReturnReset).setOnClickListener(this);
        findViewById(R.id.bReset).setOnClickListener(this);
        editTextUserName=findViewById(R.id.etUser_name_reset);
        progressBar=findViewById(R.id.progress_bar_reset);
        mAuth = FirebaseAuth.getInstance();
        editTextPassword=findViewById(R.id.etUser_password_reset);
        verificationCodeReset=findViewById(R.id.verificationCodeReset);
        passwordTextView=findViewById(R.id.passwordTextView);
        btUpdate=findViewById(R.id.btupdatePassword);
        findViewById(R.id.btupdatePassword).setEnabled(false);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyResetCode();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String username =editTextUserName.getText().toString().trim();
        switch (v.getId()){
            case R.id.bReset:
                progressBar.setVisibility(View.VISIBLE);
                resetUser(username);
                break;
            case R.id.btReturnReset:
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }
    private void VerifyResetCode(){
        Code  = verificationCodeReset.getText().toString();
        if (Code.isEmpty()){
            verificationCodeReset.setError("Code is Required");
            verificationCodeReset.requestFocus();
            return;
        }
        PhoneAuthCredential credential   = PhoneAuthProvider.getCredential(codeSent, Code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        password = editTextPassword.getText().toString();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(mAuth.getCurrentUser().getUid());

                            try {

                                databaseReference.child("password").setValue(Security.encrypt(password)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            mAuth.getCurrentUser().updatePassword(password);
                                            Toast.makeText(getApplicationContext(),"Password Changed",Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            finish();
                                            Intent intent =new Intent(ResetPassword.this,MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(ResetPassword.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(),"Incorrect Code",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    private void resetUser(final String username){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        Query query = ref.orderByChild("username").equalTo(username);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        if (username.contains("@")) {
                        mAuth.sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(ResetPassword.this, "Confirmation Email Sent", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    Intent intent = new Intent(
                                            ResetPassword.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(ResetPassword.this, "Invalid email", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }else {
                            editTextPassword.setVisibility(View.VISIBLE);
                            verificationCodeReset.setVisibility(View.VISIBLE);
                            passwordTextView.setVisibility(View.VISIBLE);
                            btUpdate.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            sendVerificationCode(username);


                        }
                    }else {
                        Toast.makeText(ResetPassword.this, "User not found", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



    }
    private void sendVerificationCode(String phone) {
        findViewById(R.id.bReset).setEnabled(false);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
        Toast.makeText(ResetPassword.this, "Code will be sent in less than one minute", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.bReset).setEnabled(true);
                    }
                });
            }
        }, 60000);
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
            findViewById(R.id.btupdatePassword).setEnabled(true);

        }
    };
}


