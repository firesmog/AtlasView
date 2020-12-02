package com.readboy.atlasview.bean;

public class Link {
    private long sourceid;
    private long targetid;
    private int id;
    private String color;

    public long getSourceid() {
        return sourceid;
    }

    public void setSourceid(long sourceid) {
        this.sourceid = sourceid;
    }

    public long getTargetid() {
        return targetid;
    }

    public void setTargetid(long targetid) {
        this.targetid = targetid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Link{" +
                "sourceid=" + sourceid +
                ", targetid=" + targetid +
                ", id=" + id +
                ", color='" + color + '\'' +
                '}';
    }
}
