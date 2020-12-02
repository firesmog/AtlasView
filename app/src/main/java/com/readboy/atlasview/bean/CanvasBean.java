package com.readboy.atlasview.bean;

public class CanvasBean {
    private double width;
    private double height;
    private double startX;
    private double endX;
    private double startY;
    private double endY;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    @Override
    public String toString() {
        return "CanvasBean{" +
                "width=" + width +
                ", height=" + height +
                ", startX=" + startX +
                ", endX=" + endX +
                ", startY=" + startY +
                ", endY=" + endY +
                '}';
    }
}
