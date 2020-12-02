package com.readboy.atlasview.bean;

public class DefaultDistrictStat {
    private int frequency;
    private float scorePercent;

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public double getScorePercent() {
        return scorePercent;
    }

    public void setScorePercent(float scorePercent) {
        this.scorePercent = scorePercent;
    }

    @Override
    public String toString() {
        return "DefaultDistrictStat{" +
                "frequency=" + frequency +
                ", scorePercent=" + scorePercent +
                '}';
    }
}
