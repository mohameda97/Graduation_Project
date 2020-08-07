package com.example.breast_app.new_amin;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Xray {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
    private String dateTime= simpleDateFormat.format(calendar.getTime());
    private String imageUrl,result;
    public Xray(){

    }

    public Xray(String dateTime, String imageUrl,String result) {
        this.dateTime = dateTime;
        this.imageUrl = imageUrl;
        this.result = result;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getResult() {
        return result;
    }
}
