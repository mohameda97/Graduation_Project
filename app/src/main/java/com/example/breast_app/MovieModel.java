package com.example.breast_app;

import android.graphics.drawable.Drawable;

public class MovieModel {
    private String title, year;
    private Drawable image;
    private Drawable topImage;
    public MovieModel() {
    }
    public MovieModel(String title, String year, Drawable image) {
        this.title = title;
        this.year = year;
        this.image = image;
        this.topImage = null;
    }
    public MovieModel(String title, String year, Drawable image, Drawable topImage) {
        this.title = title;
        this.year = year;
        this.image = image;
        this.topImage = topImage;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
        this.title = name;
    }
    public String getYear() {
        return year;
    }
    public void setImage(String year) {
        this.year = year;
    }
    public Drawable getImage() {
        return image;
    }
    public void setImage(Drawable image) {
        this.image = image;
    }
    public Drawable getTopImage() {
        return topImage;
    }
    public void setTopImage(Drawable topImage) {
        this.topImage = topImage;
    }
}
