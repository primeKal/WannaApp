package com.kalu.wannaapp.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class LocalUser implements Parcelable {
    private String name;
    private String gender;
    private String birthday;
    private String phonenumber;
    private Uri profileimg;
    private String profileimage;

    public LocalUser() {
    }

    public LocalUser(String name, String gender, String birthday, String phonenumber, Uri img) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.phonenumber = phonenumber;
        this.profileimg=img;
    }
    public LocalUser(String name, String gender, String birthday, String phonenumber, String profileimage) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.phonenumber = phonenumber;
        this.profileimage=profileimage;
    }

    public LocalUser(Parcel p){
        String[] data=new String[5];
        p.readStringArray(data);

        this.name=data[0];
        this.gender=data[1];
        this.birthday=data[2];
        this.phonenumber=data[3];
        this.profileimg=Uri.parse(data[4]);
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Uri getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(Uri profileimg) {
        this.profileimg = profileimg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name,this.gender,this.birthday,this.phonenumber,this.profileimg.toString()});
    }
    public static final Parcelable.Creator CREATOR =new Parcelable.Creator(){
        public LocalUser createFromParcel (Parcel p){
            return new LocalUser(p);
        }
        public LocalUser[] newArray (int size){
            return new LocalUser[size];
        }
    };
}
