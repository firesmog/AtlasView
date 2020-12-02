package com.readboy.atlasview.model;

import com.readboy.atlasview.bean.AtlasBean;
import com.readboy.atlasview.bean.CanvasBean;

public class TreeModel {
    private AtlasBean atlasBean;
    private CanvasBean canvasBean;

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

    @Override
    public String toString() {
        return "TreeModel{" +
                "atlasBean=" + atlasBean +
                ", canvasBean=" + canvasBean +
                '}';
    }
}
