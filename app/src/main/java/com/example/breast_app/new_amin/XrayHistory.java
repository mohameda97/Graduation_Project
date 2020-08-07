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

public class XrayHistory extends AppCompatActivity {
    FirebaseAuth mAuth;
    List<Xray> xrays;
    DatabaseReference databaseXray;
    ListView listViewXray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xray_history);
        listViewXray = findViewById(R.id.listViewXrays);
        xrays = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseXray = FirebaseDatabase.getInstance().getReference("Xray").child(id);
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseXray.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xrays.clear();
                for (DataSnapshot doctorSnapShot: dataSnapshot.getChildren()){
                    Xray xray = doctorSnapShot.getValue(Xray.class);
                    xrays.add(xray);
                }
                XrayHistoryList xrayHistoryList =new XrayHistoryList(XrayHistory.this,xrays);
                listViewXray.setAdapter(xrayHistoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
