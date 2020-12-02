package com.readboy.atlasview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

import com.google.gson.Gson;
import com.readboy.atlasview.bean.AtlasBean;
import com.readboy.atlasview.bean.AtlasMapping;
import com.readboy.atlasview.bean.CanvasBean;
import com.readboy.atlasview.bean.Node;
import com.readboy.atlasview.model.TreeModel;
import com.readboy.atlasview.utils.AssertsUtil;
import com.readboy.atlasview.utils.AtlasUtil;
import com.readboy.atlasview.view.TreeView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CanvasBean canvasBean;
    private TreeView editMapTreeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        editMapTreeView = (TreeView) findViewById(R.id.edit_map_tree_view);
        initData();
    }

    private void initData(){
        Gson gson = new Gson();
        String json = AssertsUtil.getFromAssets(MainActivity.this,"json.txt");
        AtlasBean data = gson.fromJson(json,AtlasBean.class);
        AtlasUtil.setOrderInNodes(data);
        canvasBean = AtlasUtil.getCanvasAccordAtlas(data);



        LinkedHashMap<Long, Node> models = getNodeList(data);
        TreeModel model = new TreeModel();
        model.setAtlasBean(data);
        model.setCanvasBean(canvasBean);
        editMapTreeView.setTreeModel(model);
    }

    private LinkedHashMap<Long,Node> getNodeList(AtlasBean data){
        LinkedHashMap<Long,Node> map = new LinkedHashMap<>();
        AtlasMapping mapping = data.getData().getMapping();
        List<Node> nodes =  mapping.getNodes();
        for (Node node : nodes) {
            Node model = new Node();
            model.setFont(node.getFont());
            model.setId(node.getId());
            model.setName(node.getName());
            model.setKeypoint(node.getKeypoint());
            model.setShape(node.getShape());
            model.setType(node.getType());
            model.setX(node.getX());
            model.setY(node.getY());
            model.setOrder(AtlasUtil.getOrderInNodes(mapping,node.getId()) + 1);
            model.setFloor(node.getFloor());
            map.put(node.getId(),model);
        }
        return map;
    }
}