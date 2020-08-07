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

public class RiskHistory extends AppCompatActivity {
    FirebaseAuth mAuth;
    List<Risk> risks;
    DatabaseReference databaseRisk;
    ListView listViewRisk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_history);
        listViewRisk = findViewById(R.id.listViewRisks);
        risks = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseRisk = FirebaseDatabase.getInstance().getReference("Risk").child(id);

    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseRisk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                risks.clear();
                for (DataSnapshot doctorSnapShot: dataSnapshot.getChildren()){
                    Risk risk = doctorSnapShot.getValue(Risk.class);
                    risks.add(risk);
                }
                RiskHistoryList riskHistoryList =new RiskHistoryList(RiskHistory.this,risks);
                listViewRisk.setAdapter(riskHistoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
