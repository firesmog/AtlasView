package com.readboy.atlasview.bean;

import com.google.gson.annotations.SerializedName;


public class AtlasBean {

    @SerializedName("suggest")
    private String suggest;

    @SerializedName("detail")
    private AtlasNode data;

    @SerializedName("stat")
    private AtlasStat stat;

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public AtlasNode getData() {
        return data;
    }

    public void setData(AtlasNode data) {
        this.data = data;
    }

    public AtlasStat getStat() {
        return stat;
    }

    public void setStat(AtlasStat stat) {
        this.stat = stat;
    }
}
