package com.readboy.atlasview.bean;

import com.google.gson.annotations.SerializedName;

public class AtlasData {
    @SerializedName("F_responseMsg")
    private String responseMsg;

    @SerializedName("F_responseNo")
    private int responseCode;

    @SerializedName("F_data")
    private AtlasBean data;

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public AtlasBean getData() {
        return data;
    }

    public void setData(AtlasBean data) {
        this.data = data;
    }
}
