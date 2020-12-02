package com.readboy.atlasview.bean;

import java.util.List;

public class AtlasNode {
    private List<TestPoint> testPoints;
    private String name;
    private Parent parent;
    private int courseId;
    private AtlasMapping mapping;
    private int source;
    private List<KeyPoint> keyPoint;
    private List<QstKeyPoint> qstKeypoint;
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

    public List<QstKeyPoint> getQstKeypoint() {
        return qstKeypoint;
    }

    public void setQstKeypoint(List<QstKeyPoint> qstKeypoint) {
        this.qstKeypoint = qstKeypoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AtlasNode{" +
                "testpoints=" + testPoints +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", courseId=" + courseId +
                ", mapping=" + mapping +
                ", source=" + source +
                ", keyPoint=" + keyPoint +
                ", qstKeypoint=" + qstKeypoint +
                ", id=" + id +
                '}';
    }
}
