package com.readboy.atlasview.bean;

public class KeyPoint {
    private int status;
    private int id;
    private String name;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "KeyPoint{" +
                "status=" + status +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
