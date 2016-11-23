package com.example.lw.myecard.JsonData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lw on 2016/11/21.
 */

public class CheckTimeJson {

    private int id;
    @SerializedName("checkin_time_start")
    private String checkinTimeStart;
    @SerializedName("checkin_time_end")
    private String checkinTimeEnd;
    @SerializedName("checkin_photo")
    private String checkinPhoto;
    @SerializedName("checkin_feeling")
    private String checkinFeeling;
    @SerializedName("checkout_time_start")
    private String checkoutTimeStart;
    @SerializedName("checkout_time_end")
    private String checkoutTimeEnd;
    @SerializedName("checkout_photo")
    private int checkoutPhoto;
    @SerializedName("checkout_feeling")
    private int checkoutFeeling;
    @SerializedName("device_content_spinner")
    private int deviceContentSpinner;
    @SerializedName("mqtt_host")
    private String mqttHost;
    @SerializedName("mqtt_port")
    private int mqttPort;
    private int enabled;
    @SerializedName("vclassroom_checkin_spinner")
    private int vclassroomCheckinSpinner;

    public int getId() {
        return id;
    }

    public String getCheckinTimeStart() {
        return checkinTimeStart;
    }

    public String getCheckinTimeEnd() {
        return checkinTimeEnd;
    }

    public String getCheckinPhoto() {
        return checkinPhoto;
    }

    public String getCheckinFeeling() {
        return checkinFeeling;
    }

    public String getCheckoutTimeStart() {
        return checkoutTimeStart;
    }

    public String getCheckoutTimeEnd() {
        return checkoutTimeEnd;
    }

    public int getCheckoutPhoto() {
        return checkoutPhoto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCheckinTimeStart(String checkinTimeStart) {
        this.checkinTimeStart = checkinTimeStart;
    }

    public void setCheckinTimeEnd(String checkinTimeEnd) {
        this.checkinTimeEnd = checkinTimeEnd;
    }

    public void setCheckinPhoto(String checkinPhoto) {
        this.checkinPhoto = checkinPhoto;
    }

    public void setCheckinFeeling(String checkinFeeling) {
        this.checkinFeeling = checkinFeeling;
    }

    public void setCheckoutTimeStart(String checkoutTimeStart) {
        this.checkoutTimeStart = checkoutTimeStart;
    }

    public void setCheckoutTimeEnd(String checkoutTimeEnd) {
        this.checkoutTimeEnd = checkoutTimeEnd;
    }

    public void setCheckoutPhoto(int checkoutPhoto) {
        this.checkoutPhoto = checkoutPhoto;
    }

    public void setCheckoutFeeling(int checkoutFeeling) {
        this.checkoutFeeling = checkoutFeeling;
    }

    public void setDeviceContentSpinner(int deviceContentSpinner) {
        this.deviceContentSpinner = deviceContentSpinner;
    }

    public void setMqttHost(String mqttHost) {
        this.mqttHost = mqttHost;
    }

    public void setMqttPort(int mqttPort) {
        this.mqttPort = mqttPort;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public void setVclassroomCheckinSpinner(int vclassroomCheckinSpinner) {
        this.vclassroomCheckinSpinner = vclassroomCheckinSpinner;
    }

    public int getCheckoutFeeling() {
        return checkoutFeeling;
    }

    public int getDeviceContentSpinner() {
        return deviceContentSpinner;
    }

    public String getMqttHost() {
        return mqttHost;
    }

    public int getMqttPort() {
        return mqttPort;
    }

    public int getEnabled() {
        return enabled;
    }

    public int getVclassroomCheckinSpinner() {
        return vclassroomCheckinSpinner;
    }
}
