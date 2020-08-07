package com.example.breast_app.new_amin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breast_app.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

 public static final String UserType="";
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    private static final   int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;
    EditText editTextUserName, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_amin_2);

        findViewById(R.id.tvRegisterLink).setOnClickListener(this);
        findViewById(R.id.bLogin).setOnClickListener(this);
        findViewById(R.id.btPhone).setOnClickListener(this);
        findViewById(R.id.bForgetPassword).setOnClickListener(this);
        editTextUserName=findViewById(R.id.etUser_name);
        editTextPassword=findViewById(R.id.etPassword);
        progressBar=findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



    }

    private void userLogin(){

        final String username=editTextUserName.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty()){
            editTextUserName.setError("username is required");
            editTextUserName.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("password is required");
            editTextPassword.requestFocus();
            return;
        }


        if (password.length()<8){
            editTextPassword.setError("Minimum length of password should be 8");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
            Query query = ref.orderByChild("username").equalTo(username);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                            User users = user.getValue(User.class);
                            try {
                                if (users.password.equals(Security.encrypt(password))) {
                                        signInWithEmail(username, password);
                                        progressBar.setVisibility(View.INVISIBLE);
                                } else {
                                    signInWithNewPassword(username,password);

                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){

                  finish();
                 startActivity(new Intent(this, com.example.breast_app.MainActivity.class));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btPhone:
                finish();
                startActivity(new Intent(this,phoneVerfication.class));
                break;
            case R.id.tvRegisterLink:
                finish();
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.bLogin:
                userLogin();
                break;
            case R.id.bForgetPassword:
                finish();
                startActivity(new Intent(this,ResetPassword.class));
                break;
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                final GoogleSignInAccount account = task.getResult(ApiException.class);


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
                Query query = ref.orderByChild("username").equalTo(account.getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                // do something with the individual "issues"
                                User users = user.getValue(User.class);

                                try {
                                    if (users.isVerified) {

                                        firebaseAuthWithGoogle(account);
                                        progressBar.setVisibility(View.INVISIBLE);


                                    }
                                    else {
                                        mGoogleSignInClient.signOut();
                                        Toast.makeText(MainActivity.this, "Please login first", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        else {
                            mGoogleSignInClient.signOut();
                            Toast.makeText(MainActivity.this, "Please Register first", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                                            Intent intent =new Intent(MainActivity.this, com.example.breast_app.MainActivity.class);
                                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"Authetication Failed." + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    private void signInWithEmail(String username,String password){

        if (username.contains("@")){
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user.isEmailVerified()) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());

                        databaseReference.child("isVerified").setValue(true);
                        finish();
                        Intent intent = new Intent(MainActivity.this, com.example.breast_app.MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainActivity.this, "Please verify your mail", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });
        }
        else {
            mAuth.signInWithEmailAndPassword(username+"@gmail.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());
                            databaseReference.child("isVerified").setValue(true);
                            finish();
                            Intent intent = new Intent(MainActivity.this, com.example.breast_app.MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
    private void signInWithNewPassword(String username, final String password){


        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user.isEmailVerified()) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());

                        databaseReference.child("isVerified").setValue(true);
                        try {
                            databaseReference.child("password").setValue(Security.encrypt(password));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finish();
                        Intent intent = new Intent(MainActivity.this, com.example.breast_app.MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(UserType, "email");
                        startActivity(intent);
                    }
                    else {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainActivity.this, "Please verify your mail", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
