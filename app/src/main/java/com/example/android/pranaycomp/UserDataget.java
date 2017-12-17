package com.example.android.pranaycomp;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by ashwani on 9/6/2017.
 */

public class UserDataget {
    String uname, uemail, uphonenumber;

    UserDataget(String uuid) {
        DatabaseReference myreff = FirebaseDatabase.getInstance().getReference().child("userdata/" + uuid);
        myreff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals("name"))
                        uname = (String) ds.getValue();
                    else if (ds.getKey().equals("phone"))
                        uphonenumber = (String) ds.getValue();
                    else if (ds.getKey().equals("email"))
                        uemail = (String) ds.getValue();
                    else Log.d(TAG, "onDataChange: " + ds);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getUname() {
        return uname;
    }

    public String getUemail() {
        return uemail;
    }

    public String getUphonenumber() {
        return uphonenumber;
    }
}
