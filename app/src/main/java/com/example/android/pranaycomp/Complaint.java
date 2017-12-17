package com.example.android.pranaycomp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * Created by ashwani on 9/3/2017.
 */

public class Complaint {
    String mschool, mcomplaintid, mdescription, mproblem, museremail, phone, status, userKey, downloadLink;
    Long date;

    public Complaint(String mcomplaintid, String mschool, String mproblem,
                     String mdescription, String museremail, String phoneNumber, Long date, String status, String userKey, String downloadLink) {
        this.mschool = mschool;
        this.mcomplaintid = mcomplaintid;
        this.mdescription = mdescription;
        this.mproblem = mproblem;
        this.museremail = museremail;
        phone = phoneNumber;
        this.date = date;
        this.status = status;
        this.userKey = userKey;
        this.downloadLink = downloadLink;
    }

    public Map<String, Object> getComplaintmap() {
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("complaint id", mcomplaintid);
        mp.put("school", mschool);
        mp.put("problem", mproblem);
        mp.put("description", mdescription);
        mp.put("user Email", museremail);
        mp.put("phone", phone);
        mp.put("date", date);
        mp.put("status", status);
        return mp;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public String getStatus() {
        return status;
    }

    public String getMschool() {
        return mschool;
    }

    public void setMschool(String mschool) {
        this.mschool = mschool;
    }

    public String getMcomplaintid() {
        return mcomplaintid;
    }

    public void setMcomplaintid(String mcomplaintid) {
        this.mcomplaintid = mcomplaintid;
    }

    public String getMdescription() {
        return mdescription;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }

    public String getMproblem() {
        return mproblem;
    }

    public void setMproblem(String mproblem) {
        this.mproblem = mproblem;
    }

    public String getMuseremail() {
        return museremail;
    }

    public void setMuseremail(String museremail) {
        this.museremail = museremail;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() throws ParseException {
        Date dt = new Date(date);
        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yy\nhh:mm a ");
        sfd.format(date);
        return sfd.format(date);
    }
}

class Sortbyid implements Comparator<Complaint> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(Complaint a, Complaint b) {
        return parseInt(b.mcomplaintid) - parseInt(a.mcomplaintid);
    }
}
