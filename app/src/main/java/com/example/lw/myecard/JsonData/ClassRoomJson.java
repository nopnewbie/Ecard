package com.example.lw.myecard.JsonData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lw on 2016/11/18.
 */

public class ClassRoomJson
{
    @SerializedName("classroom_name")
    private String classRoom;

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }
}
