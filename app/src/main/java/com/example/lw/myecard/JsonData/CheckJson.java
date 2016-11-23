package com.example.lw.myecard.JsonData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lw on 2016/11/22.
 */

public class CheckJson {

    public final static String CHECK_IN = "checkin";
    public final static String CHEKC_OUT = "checkout";

    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("student_name")
    private String studentName;
    private String time;
    private String type;

    public static String getCheckIn() {
        return CHECK_IN;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
