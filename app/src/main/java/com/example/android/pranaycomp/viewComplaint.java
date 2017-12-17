package com.example.android.pranaycomp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ashwani on 9/3/2017.
 */

public class viewComplaint extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //    private Complaint c;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_complaint);

        final ArrayList<Complaint> addingComplaint = new ArrayList<>();
        final ComplaintAdapter adapter = new ComplaintAdapter(this, addingComplaint);
        final ListView listView = (ListView) findViewById(R.id.complaint_list_view_layout);

        Query myReff = database.getReference().child("complaint/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/").orderByKey();
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mschool = "s", mcomplaintid = "0", mdescription = "ds", downloadLink = null,
                        mproblem = "prob", museremail = "@", phone = "0075", status = "FalseWale";
                Long date = Long.valueOf(0);


                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.d("", "onDataChange: " + dataSnapshot);
                    for (DataSnapshot dt : d.getChildren()) {
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
                    }
                    addingComplaint.add(new Complaint(mcomplaintid, mschool, mproblem, mdescription, museremail, phone, date,
                            status, FirebaseAuth.getInstance().getCurrentUser().getUid().toString(), downloadLink));
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
