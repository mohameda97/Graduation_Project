package com.example.breast_app.new_amin;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.breast_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DiagnoseHistory extends AppCompatActivity {
    FirebaseAuth mAuth;
    List<Diagnose> diagnoses;
    DatabaseReference databaseDiagnose;
    ListView listViewDiagnoses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_history);
        listViewDiagnoses = findViewById(R.id.listViewDiagnoses);
        diagnoses = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseDiagnose = FirebaseDatabase.getInstance().getReference("Diagnose").child(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseDiagnose.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                diagnoses.clear();
                for (DataSnapshot doctorSnapShot: dataSnapshot.getChildren()){
                    Diagnose diagnose = doctorSnapShot.getValue(Diagnose.class);
                    diagnoses.add(diagnose);
                }
                DiagnoseHistoryList diagnoseHistoryList =new DiagnoseHistoryList(DiagnoseHistory.this,diagnoses);
                listViewDiagnoses.setAdapter(diagnoseHistoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
