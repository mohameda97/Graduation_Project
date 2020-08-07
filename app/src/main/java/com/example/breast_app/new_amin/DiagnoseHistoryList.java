package com.example.breast_app.new_amin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.breast_app.R;

import java.util.List;

public class DiagnoseHistoryList extends ArrayAdapter<Diagnose> {

    private Activity context ;
    List<Diagnose> diagnoseList;

    public DiagnoseHistoryList(Activity context,List<Diagnose> diagnoseList){
        super(context,R.layout.activity_diagnose_history_list,diagnoseList);
        this.context=context;
        this.diagnoseList=diagnoseList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_diagnose_history_list,null,true);
        TextView texture_worst = listViewItem.findViewById(R.id.texture_worst);
        TextView radius_se = listViewItem.findViewById(R.id.radius_se);
        TextView radius_worst = listViewItem.findViewById(R.id.radius_worst);
        TextView area_se = listViewItem.findViewById(R.id.area_se);
        TextView area_worst = listViewItem.findViewById(R.id.area_worst);
        TextView concave_points_mean = listViewItem.findViewById(R.id.concave_points_mean);
        TextView concave_points_worst = listViewItem.findViewById(R.id.concave_points_worst);
        TextView dateTime = listViewItem.findViewById(R.id.diagnoseDateTime);
        TextView result = listViewItem.findViewById(R.id.diagnoseResult);
        Diagnose diagnose = diagnoseList.get(position);
        dateTime.setText("Date : "+diagnose.getDateTime());
        texture_worst.setText("Texture_worst: "+String.valueOf(diagnose.getTexture_worst()));
        radius_se.setText("radius_se: "+String.valueOf(diagnose.getRadius_se()));
        radius_worst.setText("radius_worst: "+String.valueOf(diagnose.getRadius_worst()));
        area_se.setText("area_se: "+String.valueOf(diagnose.getArea_se()));
        area_worst.setText("area_worst: "+String.valueOf(diagnose.getArea_worst()));
        concave_points_mean.setText("concave_points_mean: "+String.valueOf(diagnose.getConcave_points_mean()));
        concave_points_worst.setText("concave_points_worst: "+String.valueOf(diagnose.getConcave_points_worst()));
        result.setText("Result: " + String.valueOf(diagnose.getResult()));

        return listViewItem;
    }
}
