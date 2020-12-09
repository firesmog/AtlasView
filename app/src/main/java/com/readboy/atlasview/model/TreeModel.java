package com.readboy.atlasview.model;

import com.readboy.atlasview.bean.AtlasBean;
import com.readboy.atlasview.bean.AtlasMapping;
import com.readboy.atlasview.bean.CanvasBean;
import com.readboy.atlasview.bean.Node;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TreeModel {
    private AtlasBean atlasBean;
    private CanvasBean canvasBean;
    private LinkedHashMap<Long, Node> models;
    private AtlasMapping mapping;

    public AtlasMapping getMapping() {
        return mapping;
    }

    public void setMapping(AtlasMapping mapping) {
        this.mapping = mapping;
    }

    public AtlasBean getAtlasBean() {
        return atlasBean;
    }

    public void setAtlasBean(AtlasBean atlasBean) {
        this.atlasBean = atlasBean;
    }

    public CanvasBean getCanvasBean() {
        return canvasBean;
    }

    public void setCanvasBean(CanvasBean canvasBean) {
        this.canvasBean = canvasBean;
    }

    public LinkedHashMap<Long, Node> getModels() {
        return models;
    }

    public void setModels(LinkedHashMap<Long, Node> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return "TreeModel{" +
                "atlasBean=" + atlasBean +
                ", canvasBean=" + canvasBean +
                '}';
    }
}
