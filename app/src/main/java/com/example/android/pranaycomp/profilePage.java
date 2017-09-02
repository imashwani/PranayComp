package com.example.android.pranaycomp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ashwani on 8/31/2017.
 */

public class profilePage extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        displayuserdetails(user.getDisplayName(),user.getEmail());

    }

    private void displayuserdetails(String name,String email){

        TextView tv= (TextView) findViewById(R.id.Editprofile_username);
        try{tv.setText(name);}catch(Exception e){
            Log.d(getBaseContext().toString(), "displayuserdetails: yaha pe bhi profile name wala "+e);
        }
        tv= (TextView) findViewById(R.id.Editprofile_emailid);
        try{tv.setText(email);}catch(Exception e){
            Log.d("", "displayuserdetails: yaha pe profile eamil wala sir "+e);
        }
    }
}
