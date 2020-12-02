package com.readboy.atlasview.bean;

public class DistrictStat {
    private int frequency;
    private float scorePercent;
    private int district;

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

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "DistrictStat{" +
                "frequency=" + frequency +
                ", scorePercent=" + scorePercent +
                ", district=" + district +
                '}';
    }
}
