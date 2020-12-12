package com.readboy.atlasview.bean;

public class Font {
    private String color;
    private float size;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
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
