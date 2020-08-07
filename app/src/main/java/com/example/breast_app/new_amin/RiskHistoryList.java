package com.example.breast_app.new_amin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.breast_app.R;

import java.util.List;

public class RiskHistoryList extends ArrayAdapter<Risk> {

    private Activity context ;
    List<Risk> riskList;

    public RiskHistoryList(Activity context,List<Risk> riskList){
        super(context, R.layout.activity_risk_history_list,riskList);
        this.context=context;
        this.riskList=riskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_risk_history_list,null,true);
        TextView q1 = listViewItem.findViewById(R.id.q1);
        TextView q2 = listViewItem.findViewById(R.id.q2);
        TextView q3 = listViewItem.findViewById(R.id.q3);
        TextView q4 = listViewItem.findViewById(R.id.q4);
        TextView q5 = listViewItem.findViewById(R.id.q5);
        TextView q6 = listViewItem.findViewById(R.id.q6);
        TextView q7 = listViewItem.findViewById(R.id.q7);
        TextView q8 = listViewItem.findViewById(R.id.q8);
        TextView q9 = listViewItem.findViewById(R.id.q9);
        TextView dateTime = listViewItem.findViewById(R.id.riskDateTime);
        TextView result = listViewItem.findViewById(R.id.riskResult);
        Risk risk = riskList.get(position);
        dateTime.setText("Date : "+risk.getDateTime());
        q1.setText("q1 : "+risk.getQ1());
        q2.setText("q2 : "+risk.getQ2());
        q3.setText("q3 : "+risk.getQ3());
        q4.setText("q4 : "+risk.getQ4());
        q5.setText("q5 : "+risk.getQ5());
        q6.setText("q6 : "+risk.getQ6());
        q7.setText("q7 : "+risk.getQ7());
        q8.setText("q8 : "+risk.getQ8());
        q9.setText("q9 : "+risk.getQ9());
        result.setText("Result : " + risk.getResult());

        return listViewItem;
    }
}
