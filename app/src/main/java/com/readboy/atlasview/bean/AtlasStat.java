package com.readboy.atlasview.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AtlasStat implements Serializable {
    @SerializedName("sec_grasp")
    private float secGrasp;

    @SerializedName("diff_rank")
    private int diffRank;

    @SerializedName("study_time")
    private int studyTime;

    @SerializedName("frequency")
    private int frequency;

    @SerializedName("scorePercent")
    private float scorePercent;

    public float getSecGrasp() {
        return secGrasp;
    }

    public void setSecGrasp(float secGrasp) {
        this.secGrasp = secGrasp;
    }

    public int getDiffRank() {
        return diffRank;
    }

    public void setDiffRank(int diffRank) {
        this.diffRank = diffRank;
    }

    public int getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(int studyTime) {
        this.studyTime = studyTime;
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
}
