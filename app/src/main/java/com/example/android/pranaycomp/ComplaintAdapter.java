package com.example.android.pranaycomp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

/**
 * Created by ashwani on 9/3/2017.
 */

public class ComplaintAdapter extends ArrayAdapter<Complaint> {

    public ComplaintAdapter(@NonNull Context context, @NonNull List<Complaint> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.layout, parent, false);
        }
        final Complaint currentcomplaint = getItem(position);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), ComplaintDetails.class);
                it.putExtra("id", currentcomplaint.getMcomplaintid());
                it.putExtra("school", currentcomplaint.getMschool());
                it.putExtra("problem", currentcomplaint.getMproblem());
                it.putExtra("description", currentcomplaint.getMdescription());
                it.putExtra("email", currentcomplaint.getMuseremail());
                it.putExtra("phone", currentcomplaint.getPhone());
                it.putExtra("status", currentcomplaint.getStatus());
                it.putExtra("userkey", currentcomplaint.getUserKey());
                it.putExtra("downloadLink", currentcomplaint.getDownloadLink());
                try {
                    it.putExtra("date", currentcomplaint.getDate());
                } catch (Exception e) {
                    Log.d("", "onClick: exception date wale" + e);
                }

                Toast.makeText(getContext(), "Compalint number " + currentcomplaint.getMcomplaintid() + " Clicked",
                        Toast.LENGTH_SHORT).show();

                getContext().startActivity(it);
            }
        });

//        if(currentcomplaint.getStatus().equals("False"))
        {
//            listItemView.setBackgroundColor(Color.RED);
        }


        TextView tv = (TextView) listItemView.findViewById(R.id.layout_schoolname);
        tv.setText(currentcomplaint.getMschool());
        tv = (TextView) listItemView.findViewById(R.id.layout_complaintid);
        tv.setText(currentcomplaint.getMcomplaintid());
        tv = (TextView) listItemView.findViewById(R.id.layout_problem);
        tv.setText(currentcomplaint.getMproblem());
        tv = (TextView) listItemView.findViewById(R.id.layout_description);
        tv.setText(currentcomplaint.getMdescription());
        tv = (TextView) listItemView.findViewById(R.id.layout_useremail);
        tv.setText(currentcomplaint.getMuseremail());
        tv = (TextView) listItemView.findViewById(R.id.layout_phoneNumber);
        tv.setText(currentcomplaint.getPhone());
        tv = (TextView) listItemView.findViewById(R.id.layout_date);
        try {
            tv.setText(currentcomplaint.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return listItemView;
    }
}
