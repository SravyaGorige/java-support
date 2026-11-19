package com.example.pojo;


public class UserInfo {
    String gender;

    public UserInfo(String gender, String message) {
        this.gender=gender;
        this.message=message;


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
