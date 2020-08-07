package com.example.breast_app.new_amin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.breast_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DiagnoseTest extends AppCompatActivity {
    Button btDiagnoseTest,btReturnDiagnoseTest;
    FirebaseAuth mAuth;
    private RequestQueue requestQueue;
    DatabaseReference databaseDiagnoses;
    Calendar calendar ;
    SimpleDateFormat simpleDateFormat ;
    String dateTime;
    String result;
    String jsonResponse  ;
    EditText editTexture_worst,editRadius_se,editRadius_worst,editArea_se,editArea_worst,editConcave_points_mean,editConcave_points_worst;
    TextView textDiagnoseTestResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_test);
        btDiagnoseTest = findViewById(R.id.btDiagnoseTest);
        btReturnDiagnoseTest = findViewById(R.id.btReturnDiagnoseTest);
        editTexture_worst = findViewById(R.id.texture_worst);
        editRadius_se = findViewById(R.id.radius_se);
        editRadius_worst = findViewById(R.id.radius_worst);
        editArea_se = findViewById(R.id.area_se);
        editArea_worst = findViewById(R.id.area_worst);
        editConcave_points_mean = findViewById(R.id.concave_points_mean);
        editConcave_points_worst = findViewById(R.id.concave_points_worst);
        textDiagnoseTestResult = findViewById(R.id.diagnoseTestResult);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseDiagnoses = FirebaseDatabase.getInstance().getReference("Diagnose").child(id);
        btDiagnoseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDiagnose();
            }
        });
        btReturnDiagnoseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DiagnoseTest.this,NewExamination.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void newDiagnose() {
        if (editTexture_worst.getText().toString().trim().isEmpty()){
            editTexture_worst.setError("required field");
            editTexture_worst.requestFocus();
            return;
        }
        if (editRadius_se.getText().toString().trim().isEmpty()){
            editRadius_se.setError("required field");
            editRadius_se.requestFocus();
            return;
        }
        if (editRadius_worst.getText().toString().trim().isEmpty()){
            editRadius_worst.setError("required field");
            editRadius_worst.requestFocus();
            return;
        }
        if (editArea_se.getText().toString().trim().isEmpty()){
            editArea_se.setError("required field");
            editArea_se.requestFocus();
            return;
        }
        if (editArea_worst.getText().toString().trim().isEmpty()){
            editArea_worst.setError("required field");
            editArea_worst.requestFocus();
            return;
        }
        if (editConcave_points_mean.getText().toString().trim().isEmpty()){
            editConcave_points_mean.setError("required field");
            editConcave_points_mean.requestFocus();
            return;
        }
        if (editConcave_points_worst.getText().toString().trim().isEmpty()){
            editConcave_points_worst.setError("required field");
            editConcave_points_worst.requestFocus();
            return;
        }
        final float texture_worst = Float.parseFloat(editTexture_worst.getText().toString().trim());
        final float radius_se = Float.parseFloat(editRadius_se.getText().toString().trim());
        final float radius_worst = Float.parseFloat(editRadius_worst.getText().toString().trim());
        final float area_se = Float.parseFloat(editArea_se.getText().toString().trim());
        final float area_worst = Float.parseFloat(editArea_worst.getText().toString().trim());
        final float concave_points_mean = Float.parseFloat(editConcave_points_mean.getText().toString().trim());
        final float concave_points_worst = Float.parseFloat(editConcave_points_worst.getText().toString().trim());


        dateTime= simpleDateFormat.format(calendar.getTime());
        String data = "{"+
                "\"texture_worst\":\"" + texture_worst + "\", "+
                "\"radius_se\":\"" + radius_se + "\", "+
                "\"radius_worst\":\"" + radius_worst + "\", "+
                "\"area_se\":\"" + area_se+ "\", "+
                "\"area_worst\":\"" + area_worst + "\", "+
                "\"concave_points_mean\":\"" + concave_points_mean + "\", "+
                "\"concave_points_worst\":\"" + concave_points_worst+ "\""+
                "}";
        result = Submit(data);

        Diagnose diagnose = new Diagnose(dateTime,texture_worst,radius_se,radius_worst,area_se,area_worst,concave_points_mean,concave_points_worst,result);
        databaseDiagnoses.child(dateTime).setValue(diagnose).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DiagnoseTest.this,"Success",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DiagnoseTest.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    private String Submit(String data){
        final String saveData = data;
        String URL = "https://wa3ia.herokuapp.com/api/diagnosis";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject objres = new JSONObject(response);
                    result = objres.get("result").toString();
                    Toast.makeText(getApplicationContext(), objres.toString(), Toast.LENGTH_LONG).show();
                    textDiagnoseTestResult.setText(result);
                    databaseDiagnoses.child(dateTime).child("result").setValue(result);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Server Erorr", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return saveData == null ? null : saveData.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
        return result;
    }
}
