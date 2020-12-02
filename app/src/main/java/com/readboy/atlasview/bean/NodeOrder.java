package com.readboy.atlasview.bean;

import java.util.List;

public class NodeOrder {
    private long updateTime;
    private long createTime;
    private long difficulty;
    private List<Long> order;
    private int type;
    private int id;

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

    public long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }

    public List<Long> getOrder() {
        return order;
    }

    public void setOrder(List<Long> order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NodeOrder{" +
                "updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", difficulty=" + difficulty +
                ", order=" + order +
                ", type=" + type +
                ", id=" + id +
                '}';
    }
}
