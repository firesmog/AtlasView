package com.readboy.atlasview.bean;

public class Font {
    private String color;
    private int size;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Font{" +
                "color='" + color + '\'' +
                ", size=" + size +
                '}';
    }
}
