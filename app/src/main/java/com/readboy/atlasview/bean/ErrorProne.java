package com.readboy.atlasview.bean;

public class ErrorProne {
    private String qid;
    private String description;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ErrorProne{" +
                "qid='" + qid + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
