package com.readboy.atlasview.interfaces;

import android.view.View;

/**
 * Created by owant on 18/02/2017.
 */

public interface TreeViewItemClick {
     void onItemClick(Long id, int type, String name, int frequency, float scorePercent, float grasp);
}
