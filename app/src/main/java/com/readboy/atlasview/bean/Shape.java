package com.readboy.atlasview.bean;

public class Shape {
    private int radius;
    private String type;
    private String color;



    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "color='" + color + '\'' +
                ", radius=" + radius +
                ", type='" + type + '\'' +
                '}';
    }
}
