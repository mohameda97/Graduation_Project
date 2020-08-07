package com.example.breast_app.new_amin;

public class User  {
    public String displayname,username,age,phone,city,password,id;
    public boolean isVerified;
    public  User(){

}

    public User(String id ,String displayname, String username,String password,boolean isVerified) {
        this.id =id;
        this.displayname = displayname;
        this.username = username;
        this.password=password;
        this.isVerified=isVerified;
    }





    public void setId(String id) {
        this.id = id;
    }



    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayname() {
        return displayname;
    }

    public String getUsername() {
        return username;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

}
