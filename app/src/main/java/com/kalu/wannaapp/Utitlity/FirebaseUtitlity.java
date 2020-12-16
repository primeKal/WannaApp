package com.kalu.wannaapp.Utitlity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseUtitlity {
    FirebaseAuth kFirebaseAuth;
    FirebaseUser kFirebaseUser;
    FirebaseDatabase kFirebaseDatabase;
    FirebaseStorage kFirebaseStorage;

    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public FirebaseUser getFirebaseUser() {
        return getFirebaseAuth().getCurrentUser();
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    public FirebaseStorage getFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    public void getcurrentuser(    ){

    }
}
