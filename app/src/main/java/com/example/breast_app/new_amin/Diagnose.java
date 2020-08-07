package com.example.breast_app.new_amin;



public class Diagnose {

    private String dateTime;
    private float texture_worst,radius_se,radius_worst,area_se,area_worst,concave_points_mean,concave_points_worst;
    private String result;
    public Diagnose(){

    }



    public Diagnose(String dateTime, float texture_worst, float radius_se, float radius_worst, float area_se, float area_worst, float concave_points_mean, float concave_points_worst,String result) {
        this.dateTime = dateTime;
        this.texture_worst = texture_worst;
        this.radius_se = radius_se;
        this.radius_worst = radius_worst;
        this.area_se = area_se;
        this.area_worst = area_worst;
        this.concave_points_mean = concave_points_mean;
        this.concave_points_worst = concave_points_worst;
        this.result=result;
    }

    public String getDateTime() {
        return dateTime;
    }

    public float getTexture_worst() {
        return texture_worst;
    }

    public float getRadius_se() {
        return radius_se;
    }

    public float getRadius_worst() {
        return radius_worst;
    }

    public float getArea_se() {
        return area_se;
    }

    public float getArea_worst() {
        return area_worst;
    }

    public float getConcave_points_mean() {
        return concave_points_mean;
    }

    public float getConcave_points_worst() {
        return concave_points_worst;
    }
    public String getResult() {
        return result;
    }
}
