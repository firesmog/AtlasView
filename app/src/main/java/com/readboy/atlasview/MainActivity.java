package com.readboy.atlasview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

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
       // startActivity();
    }


    private void  startActivity(){
        try{
            Intent intent;
            intent = getPackageManager().getLaunchIntentForPackage("com.readboy.mytreeview");
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            i.setComponent(intent.getComponent());
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        model.setModels(models);
        editMapTreeView.setTreeModel(model);
        editMapTreeView.setMTreeViewItemClick(new TreeViewItemClick() {
            @Override
            public void onItemClick(View item) {
                LogUtils.d("oooo == " + ((TextView)item).getText());
            }
        });
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
            if(node.getType() == 1){
                model.setVisibility(true);
            }
            map.put(node.getId(),model);

        }
        return map;
    }
}