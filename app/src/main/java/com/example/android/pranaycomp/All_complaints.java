package com.example.android.pranaycomp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ashwani on 9/6/2017.
 */

public class All_complaints extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //    private Complaint c;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_complains);


        final List<Complaint> addingComplaint = new ArrayList<>();
        final ComplaintAdapter adapter = new ComplaintAdapter(this, addingComplaint);
        final ListView listView = (ListView) findViewById(R.id.All_complaint_list_view_layout);

        Query myReff = database.getReference().child("complaint");


        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mschool = "s", mcomplaintid = "0", mdescription = "ds", mproblem = "prob", museremail = "@",
                        phone = "0075", status = "FalseWale", userKey = null, downloadLink = null;
                Long date = Long.valueOf(0);

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.d("", "onDataChange: " + d + "\n d ka key::" + d.getKey() + "\n d ka value::" + d.getValue());

                    for (DataSnapshot dda : d.getChildren()) {

                        Log.d("", "onDataChange: " + dataSnapshot);
                        for (DataSnapshot dt : dda.getChildren()) {

                            Log.d("", "onDataChange: child ka child wala" + dt);
                            Log.d("", "onDataChange: value : " + dt.getValue().toString());
                            switch (dt.getKey()) {
                                case "complaint-id":
                                    mcomplaintid = dt.getValue().toString();
                                    break;
                                case "description":
                                    mdescription = dt.getValue().toString();
                                    break;
                                case "problem":
                                    mproblem = dt.getValue().toString();
                                    break;
                                case "school":
                                    mschool = dt.getValue().toString();
                                    break;
                                case "user Email":
                                    museremail = dt.getValue().toString();
                                    break;
                                case "phone":
                                    phone = dt.getValue().toString();
                                    break;
                                case "date":
                                    date = dt.getValue(Long.class);
                                    break;
                                case "status":
                                    status = dt.getValue().toString();
                                    break;
                                case "downloadLink":
                                    downloadLink = dt.getValue().toString();
                                    break;
                                default:
                                    Log.d("", "onDataChange: " + dt.getValue().toString());
                            }
                            userKey = d.getKey();

                        }
                        addingComplaint.add(new Complaint(mcomplaintid, mschool,
                                mproblem, mdescription, museremail, phone, date, status, userKey, downloadLink));
//                            mp.put(mcomplaintid,new Complaint(mcomplaintid,mschool,mproblem,mdescription,museremail,phone));
                    }
                    Collections.sort(addingComplaint, new Sortbyid());
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        listView.setAdapter(adapter);
    }


}


