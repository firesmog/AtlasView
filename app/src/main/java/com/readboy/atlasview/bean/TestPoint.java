package com.readboy.atlasview.bean;

import java.util.List;

public class TestPoint {
    private int status;
    private long updateTime;
    private long createTime;
    private String name;
    private List<ErrorProne> errorProne;
    private int courseId;
    private List<Explain> explain;
    private List<DistrictStat> districtStat;
    private int source;
    private  int version;
    private DefaultDistrictStat defaultDistrictStat;
    private  long id;
    private String description;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ErrorProne> getErrorProne() {
        return errorProne;
    }

    public void setErrorProne(List<ErrorProne> errorProne) {
        this.errorProne = errorProne;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<Explain> getExplain() {
        return explain;
    }

    public void setExplain(List<Explain> explain) {
        this.explain = explain;
    }

    public List<DistrictStat> getDistrictStat() {
        return districtStat;
    }

    public void setDistrictStat(List<DistrictStat> districtStat) {
        this.districtStat = districtStat;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DefaultDistrictStat getDefaultDistrictStat() {
        return defaultDistrictStat;
    }

    public void setDefaultDistrictStat(DefaultDistrictStat defaultDistrictStat) {
        this.defaultDistrictStat = defaultDistrictStat;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TestPoint{" +
                "status=" + status +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", name='" + name + '\'' +
                ", errorProne=" + errorProne +
                ", courseId=" + courseId +
                ", explain=" + explain +
                ", districtStat=" + districtStat +
                ", source=" + source +
                ", version=" + version +
                ", defaultDistrictStat=" + defaultDistrictStat +
                ", id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
