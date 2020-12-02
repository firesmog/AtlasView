package com.readboy.atlasview.bean;

public class Explain {
    private int qid;
    private int video;

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "Explain{" +
                "qid=" + qid +
                ", video=" + video +
                '}';
    }
}
