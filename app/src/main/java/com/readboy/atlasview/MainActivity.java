package com.readboy.atlasview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.readboy.atlasview.bean.AtlasBean;
import com.readboy.atlasview.bean.AtlasMapping;
import com.readboy.atlasview.bean.CanvasBean;
import com.readboy.atlasview.bean.Node;
import com.readboy.atlasview.interfaces.TreeViewItemClick;
import com.readboy.atlasview.model.TreeModel;
import com.readboy.atlasview.utils.AssertsUtil;
import com.readboy.atlasview.utils.AtlasUtil;
import com.readboy.atlasview.utils.log.LogUtils;
import com.readboy.atlasview.view.TreeView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CanvasBean canvasBean;
    private TreeView editMapTreeView;
    private RelativeLayout rlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        editMapTreeView = (TreeView) findViewById(R.id.edit_map_tree_view);
        rlMain = (RelativeLayout) findViewById(R.id.rl_main);
        initData();
    }


    private void startActivity() {
        try {
            Intent intent;
            intent = getPackageManager().getLaunchIntentForPackage("com.readboy.mytreeview");
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            i.setComponent(intent.getComponent());
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        Gson gson = new Gson();
        String json = AssertsUtil.getFromAssets(MainActivity.this, "json.txt");
        AtlasBean data = gson.fromJson(json, AtlasBean.class);
        AtlasMapping mapping = data.getData().getMapping();
        AtlasUtil.setOrderInNodes(mapping);
        AtlasUtil.setOrderFloorInNodes(mapping);

        List<Long> visibleNodeId = AtlasUtil.getVisibleCanvasAccordAtlas(mapping);
        AtlasUtil.updateVisibleMapping(mapping, visibleNodeId);

        canvasBean = AtlasUtil.getCanvasAccordAtlas(mapping);

        LinkedHashMap<Long, Node> models = AtlasUtil.updateNodeList(mapping);
        TreeModel model = new TreeModel();
        model.setMapping(mapping);
        model.setCanvasBean(canvasBean);
        model.setModels(models);
        editMapTreeView.setTouchAble(true);
        editMapTreeView.setTreeModel(model);
        editMapTreeView.setMTreeViewItemClick(new TreeViewItemClick() {
            @Override
            public void onItemClick(Long id, int type, String name, int frequency, float scorePercent, float grasp) {

            }
        });
        AtlasUtil.adapterViewSize(editMapTreeView);
    }

}