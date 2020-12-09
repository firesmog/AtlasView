package com.readboy.atlasview.bean;

public class Explain {
    private long qid;
    private long video;

    public long getQid() {
        return qid;
    }

    public void setQid(long qid) {
        this.qid = qid;
    }

    public long getVideo() {
        return video;
    }

    public void setVideo(long video) {
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
