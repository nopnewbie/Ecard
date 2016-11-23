package com.example.lw.myecard.JsonData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lw on 2016/11/18.
 */

public class NewsJson {
    private int id;

    @SerializedName("datetime")
    private String dateTime;

    @SerializedName("image")
    private String imageUrl;

    private String name;

    @SerializedName("username")
    private String userName;

    @SerializedName("document_path")
    private String documentPath;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
