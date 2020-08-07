package com.example.breast_app.new_amin;


public class Risk {
    public Risk(){

    }
    private String dateTime;
    private String result;
    private boolean q1,q2,q3;
    private int q4,q5,q6,q7,q8,q9;
    public Risk(String dateTime, boolean q1, boolean q2, boolean q3, int q4, int q5, int q6, int q7, int q8, int q9,String result) {
        this.dateTime = dateTime;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q8 = q8;
        this.q9 = q9;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean getQ1() {
        return q1;
    }

    public boolean getQ2() {
        return q2;
    }

    public boolean getQ3() {
        return q3;
    }

    public int getQ4() {
        return q4;
    }

    public int getQ5() {
        return q5;
    }

    public int getQ6() {
        return q6;
    }

    public int getQ7() {
        return q7;
    }

    public int getQ8() {
        return q8;
    }

    public int getQ9() {
        return q9;
    }
}
