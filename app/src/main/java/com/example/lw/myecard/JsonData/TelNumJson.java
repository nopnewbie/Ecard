package com.example.lw.myecard.JsonData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lw on 2016/11/28.
 */

public class TelNumJson {
    private int id;
    @SerializedName("student_code")
    private String studentCode;
    @SerializedName("role")
    private String name;
    @SerializedName("number")
    private String tel;

    public int getId() {
        return id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }
}
