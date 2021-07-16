package com.example.root.build_model.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable{
    private String mName;

    public Person(String mName) {
        this.mName = mName;
    }

    protected Person(Parcel in) {
        mName = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
    }

    @Override
    public String toString() {
        return "Person: name is "+mName;
    }
}
