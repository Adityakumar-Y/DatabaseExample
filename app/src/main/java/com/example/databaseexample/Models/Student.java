package com.example.databaseexample.Models;

import java.io.Serializable;

public class Student implements Serializable {
    protected String mName;
    protected String mEmail;
    protected String mPhone;

    public Student(String mName, String mEmail, String mPhone) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}
