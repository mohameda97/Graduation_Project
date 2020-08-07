package com.example.breast_app.new_amin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.breast_app.R;

import java.util.List;

public class XrayHistoryList extends ArrayAdapter<Xray> {

    private Activity context ;
    List<Xray> xrayList;

    public XrayHistoryList(Activity context,List<Xray> xrayList){
        super(context, R.layout.activity_xray_history_list,xrayList);
        this.context=context;
        this.xrayList=xrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_xray_history_list,null,true);

        TextView dateTime = listViewItem.findViewById(R.id.xrayDateTime);
        ImageView imageView=listViewItem.findViewById(R.id.xrayImage);
        TextView result = listViewItem.findViewById(R.id.xrayResult);
        Xray xray = xrayList.get(position);
        dateTime.setText("Date : "+xray.getDateTime());
        Glide.with(context).load(xray.getImageUrl().toString()).into(imageView);
        result.setText("Result : " + xray.getResult());
        return listViewItem;
    }

}
