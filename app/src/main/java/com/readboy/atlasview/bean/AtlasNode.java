package com.readboy.atlasview.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AtlasNode {
    @SerializedName("testpoints")
    private List<TestPoint> testPoints;

    @SerializedName("name")
    private String name;

    @SerializedName("parent")
    private Parent parent;

    @SerializedName("courseId")
    private int courseId;

    @SerializedName("mapping")
    private AtlasMapping mapping;

    @SerializedName("source")
    private int source;

    @SerializedName("keypoint")
    private List<KeyPoint> keyPoint;

    @SerializedName("qstKeypoint")
    private List<QstKeyPoint> qstKeyPoint;

    @SerializedName("id")
    private int id;

    public List<TestPoint> getTestPoints() {
        return testPoints;
    }

    public void setTestPoints(List<TestPoint> testPoints) {
        this.testPoints = testPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


    public AtlasMapping getMapping() {
        return mapping;
    }

    public void setMapping(AtlasMapping mapping) {
        this.mapping = mapping;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public List<KeyPoint> getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(List<KeyPoint> keyPoint) {
        this.keyPoint = keyPoint;
    }

    public List<QstKeyPoint> getQstKeyPoint() {
        return qstKeyPoint;
    }

    public void setQstKeyPoint(List<QstKeyPoint> qstKeyPoint) {
        this.qstKeyPoint = qstKeyPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
