package com.example.todomobile.model;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {
    private String userId;
    private String userEmail;
    private String userName;
    private String userPassword;

    public User(){

    }

    public User(String userId, String userEmail, String userName, String userPassword) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    protected User(Parcel in) {
        userId = in.readString();
        userEmail = in.readString();
        userName = in.readString();
        userPassword = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(userEmail);
        parcel.writeString(userName);
        parcel.writeString(userPassword);
    }
}
