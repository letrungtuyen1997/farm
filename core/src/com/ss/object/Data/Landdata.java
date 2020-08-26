package com.ss.object.Data;

import com.google.gson.annotations.SerializedName;

public class Landdata {
    @SerializedName("Status")
    int Status;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
