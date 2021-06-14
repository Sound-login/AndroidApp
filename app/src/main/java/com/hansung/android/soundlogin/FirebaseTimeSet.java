package com.hansung.android.soundlogin;

public class FirebaseTimeSet {
    public String time;
    public String name;


    public FirebaseTimeSet(){
        // Default constructor required for c, alls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebaseTimeSet(String time, String name) {
        this.time = time;
        this.name = name;

    }
}
