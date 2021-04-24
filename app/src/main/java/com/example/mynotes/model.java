package com.example.mynotes;

import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class model {

    String title,desc,cName,date;
    int id,cpos;
    String uri;

    public model(String title, String desc, int id, String cName, int cpos, String uri,String date) {
        this.title = title;
        this.desc = desc;
        this.id = id;
        this.cName = cName;
        this.cpos = cpos;
        this.uri = uri;
        this.date = date;



    }

    public String getDate() throws ParseException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // String to Date:
        String input = date;
        Date dates = sdf.parse(input);

        // Date to String:
        String strDate = sdf.format(dates);

        return strDate;
    }



    public void setDate(String date) {

        this.date = date;
    }

    public int getCpos() {
        return cpos;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setCpos(int cpos) {
        this.cpos = cpos;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String categoryName) {
        this.cName = categoryName;
    }

    public String getTitle() {
        //Log.i("ttttttttttttttt",title);
        return title;
    }



    public String getDesc() {
        return desc;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(int id) {
        this.id = id;
    }
}
