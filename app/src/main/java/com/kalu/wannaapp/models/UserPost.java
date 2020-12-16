package com.kalu.wannaapp.models;

import com.google.firebase.database.ServerValue;

public class UserPost {
    String name;
    Object timestamp= ServerValue.TIMESTAMP;
    String discription;
    String postimage;
    String phone;
    String userimage;
    String uid;

    public UserPost() {
    }

    public UserPost(String name, String discription, String postimage, String phone, String userimage) {
        this.name = name;
        this.discription = discription;
        this.postimage = postimage;
        this.phone = phone;
        this.userimage = userimage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }
}
