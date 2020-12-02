package com.readboy.atlasview.bean;

public class QstKeyPoint {
    private int count;
    private int id;
    private String name;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
        return "QstKeyPoint{" +
                "count=" + count +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
