package com.ss.object.Data;

import com.google.gson.annotations.SerializedName;

public class Vegdata {

    @SerializedName("x")
    float x;

    @SerializedName("y")
    float y;

    @SerializedName("type")
    String type;

    @SerializedName("id")
    int id;

    @SerializedName("index")
    int index;

    @SerializedName("second")
    int second;

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }



    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }








}
