package com.example.android.pranaycomp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by ashwani on 10/1/2017.
 */

public class ComplaintDetails extends AppCompatActivity {

    String complaintID = "", userKey = null;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_details);

        Intent it = new Intent();
        complaintID = getIntent().getStringExtra("id").toString();
        userKey = getIntent().getStringExtra("userkey").toString();


        TextView tv = (TextView) findViewById(R.id.complaint_details_complaintid);
        tv.setText(complaintID);

        tv = (TextView) findViewById(R.id.complaint_details_schoolname);
        tv.setText(getIntent().getStringExtra("school").toString());

        tv = (TextView) findViewById(R.id.complaint_details_problem);
        tv.setText(getIntent().getStringExtra("problem").toString());

        tv = (TextView) findViewById(R.id.complaint_details_description);
        tv.setText(getIntent().getStringExtra("description").toString());

        tv = (TextView) findViewById(R.id.complaint_details_useremail);
        tv.setText(getIntent().getStringExtra("email").toString());

        tv = (TextView) findViewById(R.id.complaint_details_phoneNumber);
        tv.setText(getIntent().getStringExtra("phone").toString());

        tv = (TextView) findViewById(R.id.complaint_details_date);
        tv.setText(getIntent().getStringExtra("date").toString());

        ImageView iv = (ImageView) findViewById(R.id.complaint_detail_image);

        Glide.with(this)
                .load(getIntent().getStringExtra("downloadLink"))
                .into(iv);
        iv.setVisibility(View.VISIBLE);


        final Switch sw = (Switch) findViewById(R.id.complaint_details_switch_status);
        if (getIntent().getStringExtra("status").equals("FALSE")) {
            sw.setChecked(FALSE);
        } else sw.setChecked(TRUE);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked == FALSE) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintDetails.this);

// 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.dialog_msg)
                            .setTitle(R.string.dialog_title);
                    builder.setPositiveButton("Yes,Proceed", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            DatabaseReference myReff = database.getReference().child("complaint/" + userKey +
                                    "/" + complaintID + "/status/");
                            myReff.setValue("FALSE");
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog

                        }
                    });
// Set other dialog properties

// Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(ComplaintDetails.this, "This Feature is not available for you",
                            Toast.LENGTH_LONG).show();
                    sw.setChecked(!isChecked);
                }
            }
        });


    }
}























