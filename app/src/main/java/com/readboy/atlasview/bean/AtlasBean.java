package com.readboy.atlasview.bean;

public class AtlasBean {
    private String msg;
    private AtlasNode data;
    private int ok;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AtlasNode getData() {
        return data;
    }

    public void setData(AtlasNode data) {
        this.data = data;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "AtlasBean{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", ok=" + ok +
                '}';
    }
}
