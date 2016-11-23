package com.example.lw.myecard.JsonData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lw on 2016/11/22.
 */

public class StudentJson {
    private String code;
    private String name;
    private String sex;
    private String birthday;
    @SerializedName("checkin_time")
    private String checkinTime;
    private String pic;
    @SerializedName("error_code")
    private Integer errorCode;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public String getPic() {
        return pic;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
