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

public class RiskTest extends AppCompatActivity {
    Button btRiskTest,btReturnRiskTest;
    FirebaseAuth mAuth;
    DatabaseReference databaseRisks;
    Calendar calendar ;
    SimpleDateFormat simpleDateFormat ;
    String dateTime;
    String result;
    TextView textRiskTestResult;
    private RequestQueue requestQueue;
    String q1="";
    String q2="";
    String q3="";
    String q4="";
    String q5="";
    String q6="";
    String q7="";
    String q8="";
    String q9="";
    boolean question1 =false;
    boolean question2 =false;
    boolean question3 =false;
    int question4,question5,question6,question7,question8,question9 ;
    Button bQ1True,bQ1False,bQ2True,bQ2False,bQ3True,bQ3False,btQ4_1,btQ4_2,btQ4_3,btQ4_4,btQ5_1,btQ5_2,btQ5_3,btQ5_4,btQ5_5,btQ5_6,btQ6_1,btQ6_2,btQ6_3,btQ7_1,btQ7_2,btQ8_1,btQ8_2,btQ9_1,btQ9_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_test);
        btRiskTest = findViewById(R.id.btRiskTest);
        btReturnRiskTest = findViewById(R.id.btReturnRiskTest);
        bQ1True = findViewById(R.id.true_0);
        bQ1False = findViewById(R.id.false_0);
        bQ2True = findViewById(R.id.true_1);
        bQ2False = findViewById(R.id.false_1);
        bQ3True = findViewById(R.id.true_2);
        bQ3False = findViewById(R.id.false_2);
        btQ4_1 = findViewById(R.id.unknown_0);
        btQ4_2 = findViewById(R.id.answer_01);
        btQ4_3 = findViewById(R.id.answer_02);
        btQ4_4 = findViewById(R.id.answer_03);
        btQ5_1 = findViewById(R.id.unknown_1);
        btQ5_2 = findViewById(R.id.answer_11);
        btQ5_3 = findViewById(R.id.answer_12);
        btQ5_4 = findViewById(R.id.answer_13);
        btQ5_5 = findViewById(R.id.answer_14);
        btQ5_6 = findViewById(R.id.answer_15);
        btQ6_1 = findViewById(R.id.no_0);
        btQ6_2 = findViewById(R.id.one_0);
        btQ6_3 = findViewById(R.id.greater_0);
        btQ7_1 = findViewById(R.id.no_1);
        btQ7_2 = findViewById(R.id.yes_1);
        btQ8_1 = findViewById(R.id.one_2);
        btQ8_2 = findViewById(R.id.greater_2);
        btQ9_1 = findViewById(R.id.no_2);
        btQ9_2 = findViewById(R.id.yes_2);

        textRiskTestResult= findViewById(R.id.riskTestResult);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseRisks = FirebaseDatabase.getInstance().getReference("Risk").child(id);
        btRiskTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRisk();
            }
        });
        bQ1True.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q1 = "true";
                question1 = true;
                bQ1True.setEnabled(false);
                bQ1False.setEnabled(true);
            }
        });
        bQ1False.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q1 = "false";
                question1 = false;
                bQ1False.setEnabled(false);
                bQ1True.setEnabled(true);
            }
        });
        bQ2True.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q2 = "true";
                question2 = true;
                bQ2True.setEnabled(false);
                bQ2False.setEnabled(true);
            }
        });
        bQ2False.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q2 = "false";
                question2 = false;
                bQ2False.setEnabled(false);
                bQ2True.setEnabled(true);

            }
        });
        bQ3True.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q3 = "true";
                question3 = true;
                bQ3True.setEnabled(false);
                bQ3False.setEnabled(true);
            }
        });
        bQ3False.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q3 = "false";
                question3 = false;
                bQ3False.setEnabled(false);
                bQ3True.setEnabled(true);
            }
        });
        btQ4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q4 = "3";
                question4 = 3;
                btQ4_1.setEnabled(false);
                btQ4_2.setEnabled(true);
                btQ4_3.setEnabled(true);
                btQ4_4.setEnabled(true);

            }
        });
        btQ4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q4 = "1";
                question4 = 1;
                btQ4_2.setEnabled(false);
                btQ4_1.setEnabled(true);
                btQ4_3.setEnabled(true);
                btQ4_4.setEnabled(true);

            }
        });
        btQ4_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q4 = "0";
                question4 = 0;
                btQ4_3.setEnabled(false);
                btQ4_2.setEnabled(true);
                btQ4_1.setEnabled(true);
                btQ4_4.setEnabled(true);

            }
        });
        btQ4_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q4 = "2";
                question4 = 2;
                btQ4_4.setEnabled(false);
                btQ4_2.setEnabled(true);
                btQ4_3.setEnabled(true);
                btQ4_1.setEnabled(true);

            }
        });
        btQ5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q5 = "5";
                question5 = 5;
                btQ5_1.setEnabled(false);
                btQ5_2.setEnabled(true);
                btQ5_3.setEnabled(true);
                btQ5_4.setEnabled(true);
                btQ5_5.setEnabled(true);
                btQ5_6.setEnabled(true);

            }
        });
        btQ5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q5 = "4";
                question5 = 4;
                btQ5_1.setEnabled(true);
                btQ5_2.setEnabled(false);
                btQ5_3.setEnabled(true);
                btQ5_4.setEnabled(true);
                btQ5_5.setEnabled(true);
                btQ5_6.setEnabled(true);
            }
        });
        btQ5_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q5 = "2";
                question5 = 2;
                btQ5_3.setEnabled(false);
                btQ5_2.setEnabled(true);
                btQ5_1.setEnabled(true);
                btQ5_4.setEnabled(true);
                btQ5_5.setEnabled(true);
                btQ5_6.setEnabled(true);

            }
        });
        btQ5_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q5 = "0";
                question5 = 0;
                btQ5_4.setEnabled(false);
                btQ5_2.setEnabled(true);
                btQ5_3.setEnabled(true);
                btQ5_1.setEnabled(true);
                btQ5_5.setEnabled(true);
                btQ5_6.setEnabled(true);

            }
        });
        btQ5_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q5 = "1";
                question5 = 1;
                btQ5_5.setEnabled(false);
                btQ5_2.setEnabled(true);
                btQ5_3.setEnabled(true);
                btQ5_4.setEnabled(true);
                btQ5_1.setEnabled(true);
                btQ5_6.setEnabled(true);

            }
        });
        btQ5_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q5 = "3";
                question5 = 3;
                btQ5_6.setEnabled(false);
                btQ5_2.setEnabled(true);
                btQ5_3.setEnabled(true);
                btQ5_4.setEnabled(true);
                btQ5_5.setEnabled(true);
                btQ5_1.setEnabled(true);

            }
        });
        btQ6_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q6 = "3";
                question6 = 1;
                btQ6_1.setEnabled(false);
                btQ6_2.setEnabled(true);
                btQ6_3.setEnabled(true);

            }
        });
        btQ6_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q6 = "2";
                question6 = 2;
                btQ6_2.setEnabled(false);
                btQ6_1.setEnabled(true);
                btQ6_3.setEnabled(true);

            }
        });
        btQ6_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q6 = "0";
                question6 = 0;
                btQ6_3.setEnabled(false);
                btQ6_1.setEnabled(true);
                btQ6_2.setEnabled(true);
            }
        });
        btQ7_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q7 = "0";
                question7 = 0;
                btQ7_1.setEnabled(false);
                btQ7_2.setEnabled(true);
            }
        });
        btQ7_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q7 = "1";
                question7 = 1;
                btQ7_2.setEnabled(false);
                btQ7_1.setEnabled(true);

            }
        });
        btQ8_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q8 = "1";
                question8 = 1;
                btQ8_1.setEnabled(false);
                btQ8_2.setEnabled(true);

            }
        });
        btQ8_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q8 = "0";
                question8 = 0;
                btQ8_2.setEnabled(false);
                btQ8_1.setEnabled(true);

            }
        });
        btQ9_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q9 = "1";
                question9 = 1;
                btQ9_1.setEnabled(false);
                btQ9_2.setEnabled(true);

            }
        });
        btQ9_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q9 = "2";
                question9 = 2;
                btQ9_2.setEnabled(false);
                btQ9_1.setEnabled(true);

            }
        });
        btReturnRiskTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RiskTest.this,NewExamination.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void newRisk() {


        if (q1.isEmpty()){
            Toast.makeText(RiskTest.this,"please answer all questions1",Toast.LENGTH_SHORT).show();
            return;
        }
        if (q2.isEmpty()){
            Toast.makeText(RiskTest.this,"please answer all questions",Toast.LENGTH_SHORT).show();

            return;
        }
        if (q3.isEmpty()){
            Toast.makeText(RiskTest.this,"please answer all questions",Toast.LENGTH_SHORT).show();

            return;
        }
        if (q4.isEmpty()){
            Toast.makeText(RiskTest.this,"please answer all questions",Toast.LENGTH_SHORT).show();
            return;
        }
        if (q5.isEmpty()){
            Toast.makeText(RiskTest.this,"please answer all questions",Toast.LENGTH_SHORT).show();
            return;
        }
        if (q6.isEmpty()){
            Toast.makeText(RiskTest.this,"please answer all questions",Toast.LENGTH_SHORT).show();
            return;
        }
        if (q7.isEmpty()){
            Toast.makeText(RiskTest.this,"please answer all questions",Toast.LENGTH_SHORT).show();
            return;
        }
        if (q8.isEmpty()){
            Toast.makeText(RiskTest.this,"please answer all questions",Toast.LENGTH_SHORT).show();
            return;
        }
        if (q9.isEmpty()){
            Toast.makeText(RiskTest.this,"please answer all questions",Toast.LENGTH_SHORT).show();
            return;
        }

        dateTime= simpleDateFormat.format(calendar.getTime());
        String data = "{"+
                "\"question1\":" + question1 + ", "+
                "\"question2\":" + question2 + ", "+
                "\"question3\":" + question3 + ", "+
                "\"question4\":" + question4+ ", "+
                "\"question5\":" + question5 + ", "+
                "\"question6\":" + question6 + ", "+
                "\"question7\":" + question7 + ", "+
                "\"question8\":" + question8 + ", "+
                "\"question9\":" + question9 +
                "}";
        result = Submit(data);
        Risk risk = new Risk(dateTime,question1,question2,question3,question4,question5,question6,question7,question8,question9,result);
        databaseRisks.child(dateTime).setValue(risk).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RiskTest.this,"Success",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RiskTest.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });;
 }
    private String Submit(String data){
        final String saveData = data;
        String URL = "https://wa3ia.herokuapp.com/api/risk";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject objres = new JSONObject(response);
                    result = objres.get("result").toString();
                    Toast.makeText(getApplicationContext(), objres.toString(), Toast.LENGTH_LONG).show();
                    textRiskTestResult.setText(result);
                    databaseRisks.child(dateTime).child("result").setValue(result);
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
