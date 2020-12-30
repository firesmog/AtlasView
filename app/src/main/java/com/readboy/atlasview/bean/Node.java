package com.readboy.atlasview.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Node implements Comparator<Node> {
    private String name;
    private Shape shape;
    private double x;
    private double y;
    private Font font;
    private int type;
    private long id;
    private long keypoint;
    private int order;
    private transient boolean focus = false;
    private int floor;
    @SerializedName("fp")
    private int frequency;

    @SerializedName("scorePercent")
    private float scorePercent;

    @SerializedName("grasp")
    private float grasp;

    @SerializedName("visibility")
    private boolean visibility;
    private boolean is_study;

    @SerializedName("recommand_point")
    private boolean isRecommend;

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public float getScorePercent() {
        return scorePercent;
    }

    public void setScorePercent(float scorePercent) {
        this.scorePercent = scorePercent;
    }

    public float getGrasp() {
        return grasp;
    }

    public void setGrasp(float grasp) {
        this.grasp = grasp;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getKeypoint() {
        return keypoint;
    }

    public void setKeypoint(long keypoint) {
        this.keypoint = keypoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isIs_study() {
        return is_study;
    }

    public void setIs_study(boolean is_study) {
        this.is_study = is_study;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", shape=" + shape +
                ", x=" + x +
                ", y=" + y +
                ", font=" + font +
                ", type=" + type +
                ", id=" + id +
                ", keypoint=" + keypoint +
                ", order=" + order +
                ", focus=" + focus +
                ", floor=" + floor +
                ", frequency=" + frequency +
                ", scorePercent=" + scorePercent +
                ", grasp=" + grasp +
                ", visibility=" + visibility +
                ", is_study=" + is_study +
                '}';
    }

    @Override
    public int compare(Node o1, Node o2) {
        if (o2.getOrder() < o1.getOrder()) {
            return 1;
        }
        if (o2.getOrder() > o1.getOrder()) {
            return -1;
        }
        return 0;
    }
}
