package com.example.lw.myecard.JsonData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lw on 2016/11/27.
 */

public class CoursesJson {
    private int id;
    @SerializedName("classroom_id")
    private int classroomId;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private int timing;

    public int getId() {
        return id;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public String getMonday() {
        return monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public String getFriday() {
        return friday;
    }

    public int getTiming() {
        return timing;
    }
}
