package com.readboy.atlasview.interfaces;

import android.view.View;

/**
 * Created by owant on 24/02/2017.
 */

public interface TreeViewItemLongClick {
    void onLongClick(Long id, int type, String name, int frequency, float scorePercent, float grasp);
}
