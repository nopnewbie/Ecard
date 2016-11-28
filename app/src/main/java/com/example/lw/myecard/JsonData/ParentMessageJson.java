package com.example.lw.myecard.JsonData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lw on 2016/11/27.
 */

public class ParentMessageJson {
    private int id;
    private String message;
    @SerializedName("datetime")
    private String dateTime;

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return dateTime;
    }
}
