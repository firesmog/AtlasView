package com.readboy.atlasview.bean;

public class Section {
    private String name;
    private int id;
    private int source;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Section{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", source=" + source +
                '}';
    }
}
