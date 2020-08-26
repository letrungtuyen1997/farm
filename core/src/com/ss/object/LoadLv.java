package com.ss.object;

import com.google.gson.annotations.SerializedName;

public class LoadLv {


    @SerializedName("status0")
    public int[] status0;

    @SerializedName("status1")
    public int[] status1;

    @SerializedName("status2")
    public int[] status2;

    public int[] getStatus0() {
        return status0;
    }

    public void setStatus0(int[] status0) {
        this.status0 = status0;
    }

    public int[] getStatus1() {
        return status1;
    }

    public void setStatus1(int[] status1) {
        this.status1 = status1;
    }

    public int[] getStatus2() {
        return status2;
    }

    public void setStatus2(int[] status2) {
        this.status2 = status2;
    }





}
