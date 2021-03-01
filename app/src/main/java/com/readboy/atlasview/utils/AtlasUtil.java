package com.readboy.atlasview.utils;


import android.view.ViewGroup;

import com.readboy.atlasview.bean.AtlasBean;
import com.readboy.atlasview.bean.AtlasMapping;
import com.readboy.atlasview.bean.CanvasBean;
import com.readboy.atlasview.bean.Link;
import com.readboy.atlasview.bean.Node;
import com.readboy.atlasview.bean.NodeOrder;
import com.readboy.atlasview.utils.log.LogUtils;
import com.readboy.atlasview.view.TreeView;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtlasUtil {
    private static List<Long> knowledgePointId = new ArrayList<>();
    private static List<Node> results = new ArrayList<>();

    //找到图谱中的所有知识点
    public static List<Node> findKnowledgeNode(AtlasBean bean) {
        if (null == bean) {
            return null;
        }
        List<Node> result = new ArrayList<>();
        List<Node> org = bean.getData().getMapping().getNodes();
        for (Node node : org) {
            if (node.getType() == 1) {
                result.add(node);
            }
        }
        return result;
    }

    //找到图谱中的所有根节点
    public static List<Node> findRootKnowledgeNode(AtlasBean bean) {
        if (null == bean) {
            return null;
        }

        List<Node> nodeList = bean.getData().getMapping().getNodes();
        List<Link> links = bean.getData().getMapping().getLinks();

        if (null == nodeList || nodeList.size() == 1) {
            return nodeList;
        }


        Iterator<Node> iterator = nodeList.iterator();
        inner:
        while (iterator.hasNext()) {
            Node node = iterator.next();
            for (Link link : links) {
                if (link.getTargetid() == node.getId()) {
                    iterator.remove();
                    continue inner;
                }
            }
        }
        return nodeList;
    }


    //根据nodeId 获取子节点
    public static LinkedList<Node> getSubNodeAccordId(List<Link> links, long nodeId, HashMap<Long, Node> nodeMap) {
        LinkedList<Node> nodes = new LinkedList<>();
        for (Link link : links) {
            if (link.getSourceid() == nodeId) {
                //targetId 才是子view
                if (nodeMap.get(link.getTargetid()) != null) {
                    nodes.add(nodeMap.get(link.getTargetid()));
                }
            }
        }
        Collections.sort(nodes, new Node());
        return nodes;
    }


    public static Node getParentNodeAccordId(List<Link> links, long nodeId, HashMap<Long, Node> nodeMap) {
        Node parent = new Node();
        for (Link link : links) {
            if (link.getTargetid() == nodeId) {
                //targetId 才是子view
                parent = nodeMap.get(link.getSourceid());
            }
        }
        return parent;
    }

    public static LinkedList<Node> getBrotherNodeAccordId(Node node, HashMap<Long, Node> nodeMap) {
        LinkedList<Node> brothers = new LinkedList<>();
        for (Map.Entry<Long, Node> longNodeEntry : nodeMap.entrySet()) {
            Node brother = longNodeEntry.getValue();
            if (brother.getFloor() == node.getFloor()) {
                brothers.add(brother);
            }
        }

        Collections.sort(brothers, new Node());
        return brothers;
    }


    public static int getOrderInNodes(AtlasMapping mapping, long id) {
        List<Long> orders = new ArrayList<>();
        for (NodeOrder nodeOrder : mapping.getNodeOrder()) {
            if (nodeOrder.getType() == 2) {
                orders = nodeOrder.getOrder();
            }
        }
        if (orders.size() == 0) {
            orders = mapping.getNodeOrder().get(0).getOrder();
        }
        return orders.indexOf(id) + 1;
    }

    public static void setOrderInNodes(AtlasBean data) {
        AtlasMapping mapping = data.getData().getMapping();
        List<Node> nodes = mapping.getNodes();
        List<Long> orders = new ArrayList<>();
        for (NodeOrder nodeOrder : mapping.getNodeOrder()) {
            if (nodeOrder.getType() == 2) {
                orders = nodeOrder.getOrder();
            }
        }
        if (orders.size() == 0) {
            orders = mapping.getNodeOrder().get(0).getOrder();
        }
        for (Node node : nodes) {
            node.setOrder(orders.indexOf(node.getId()) + 1);
            if (node.getType() == 1) {
                node.setVisibility(true);
            }
        }
    }

    public static void setNoRecommendNode(AtlasMapping mapping){
        if(null != mapping){
            for (Node node : mapping.getNodes()) {
                node.setRecommend(false);
            }
        }
    }

    public static void setOrderInNodes(AtlasMapping mapping) {
        List<Node> nodes = mapping.getNodes();
        List<Long> orders = new ArrayList<>();
        List<Node> knowledgeNodes = new ArrayList<>();
        Node recommendNode = null;

        for (NodeOrder nodeOrder : mapping.getNodeOrder()) {
            if (nodeOrder.getType() == 2) {
                orders = nodeOrder.getOrder();

            }
        }
        if (orders.size() == 0) {
            orders = mapping.getNodeOrder().get(0).getOrder();
        }
        for (Node node : nodes) {
            node.setOrder(orders.indexOf(node.getId()) + 1);
            if (node.getType() == 1) {
                node.setVisibility(true);
                knowledgeNodes.add(node);
            }

            if(node.isRecommend() ){
                recommendNode = node;
            }
        }

        Collections.sort(knowledgeNodes, new Node());
        if(null == recommendNode ){
            knowledgeNodes.get(0).setRecommend(true);
            recommendNode = knowledgeNodes.get(0);

        }else {

            for (int i = 0;null != knowledgeNodes && knowledgeNodes.size() > 0 && i < knowledgeNodes.size(); i++) {
                Node notStudy = knowledgeNodes.get(i);

                if(!recommendNode.isIs_study() && recommendNode.getKeypoint() == notStudy.getId() && !notStudy.isIs_study()){
                    notStudy.setRecommend(true);
                    recommendNode.setRecommend(false);
                    return;

                }
                if(!knowledgeNodes.get(i).isIs_study()){
                    int index1 = orders.indexOf(notStudy);
                    int index2 = orders.indexOf(recommendNode);
                    if(index2 > index1){
                        recommendNode.setRecommend(false);
                        notStudy.setRecommend(true);
                    }
                }
            }
        }
        LogUtils.d("setOrderInNodes == " + recommendNode.toString() );
    }

    public static void setOrderFloorInNodes(AtlasMapping mapping) {
        int count = 1;
        List<Node> nodes = mapping.getNodes();
        List<Node> result = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getType() == 1) {
                result.add(node);
            }
        }
        Collections.sort(result, new Node());
        for (Node node : result) {
            node.setFloor(count++);
        }
    }


    public static void initRecommendNode(AtlasMapping mapping) {
        int count = 1;
        List<Node> nodes = mapping.getNodes();
        List<Node> result = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getType() == 1) {
                result.add(node);
            }
        }
        Collections.sort(result, new Node());
        for (Node node : result) {
            node.setFloor(count++);
        }
    }

    //按照后台提供的已学考点（或知识点）的id，找出所有需要展示的链路上的节点（可重复）保存起来
    public static List<Node> findQuestionNode(AtlasBean bean, List<Long> ids) {
        if (bean == null) {
            LogUtils.d("findFirstQueNode findQuestionNode is null");
            return null;
        }
        results = new ArrayList<>();
        knowledgePointId = new ArrayList<>();
        List<Node> knowledgeNodes = findKnowledgeNode(bean);
        if (ids == null || ids.size() == 0) {
            return knowledgeNodes;
        }
        results.addAll(knowledgeNodes);
        List<Node> org = bean.getData().getMapping().getNodes();
        List<Link> links = bean.getData().getMapping().getLinks();

        //先将已经学过的知识点筛选出来，全部考点都需要展示
        for (Node knowledgeNode : knowledgeNodes) {
            for (Long id : ids) {
                if (id == knowledgeNode.getId()) {
                    knowledgePointId.add(id);
                    results.addAll(findAllPointAccordKnowledge(id, org));
                }
            }
        }

        //再将非知识点的考点链路上的考点筛选出来。
        for (Long id : ids) {
            //1、跳过已经筛选过的知识点，只对考点做处理
            if (knowledgePointId.contains(id)) {
                continue;
            }

            //2、若当前考点属于已经筛选过的知识点，则跳过该考点
            Node curQueNode = findQueNodeAccordId(id, org);
            if (curQueNode != null && knowledgePointId.contains(curQueNode.getKeypoint())) {
                continue;
            }

            findAllPointAccordQue(id, org, links);
        }

        return results;
    }

    //知识点已学，需获取该知识点所有已学节点链路
    private static List<Node> findAllPointAccordKnowledge(long id, List<Node> org) {
        List<Node> results = new ArrayList<>();
        for (Node node : org) {
            if (node.getKeypoint() == id) {
                results.add(node);
            }
        }
        return results;
    }

    //倒叙递归（直至节点为知识点），获取该考点链路上的所有节点
    private static void findAllPointAccordQue(long id, List<Node> org, List<Link> links) {
        Node curNode = findQueNodeAccordId(id, org);
        if (curNode == null || curNode.getType() == 1) {
            LogUtils.d("findAllPointAccordQue end");
            return;
        }
        results.add(curNode);
        for (Link link : links) {

            if (link.getTargetid() == id) {
                findAllPointAccordQue(link.getSourceid(), org, links);
            }
        }
    }

    //通过id找出节点
    private static Node findQueNodeAccordId(long id, List<Node> org) {
        for (Node node1 : org) {
            if (node1.getId() == id) {
                return node1;
            }
        }
        return null;
    }

    //筛选从后套获取的图谱数据
    public static AtlasBean getFilterAtlasBean(AtlasBean bean, List<Long> ids) {
        List<Node> filterNode = findQuestionNode(bean, ids);
        if (filterNode == null) {
            LogUtils.d("getFilterAtlasBean is null");
            return null;
        }

        List<Node> org = bean.getData().getMapping().getNodes();
        Iterator<Node> it = org.iterator();

        while (it.hasNext()) {
            Node next = it.next();

            if (!filterNode.contains(next)) {
                it.remove();
            }
        }
        return bean;
    }

    public static AtlasBean setNodeFloor(AtlasBean bean, List<Node> root) {
        AtlasMapping map = bean.getData().getMapping();
        Deque<Node> deque = new ArrayDeque<>();

        HashMap<Long, Node> hashMap = new HashMap();


        for (Node node : map.getNodes()) {
            hashMap.put(node.getId(), node);
        }

        for (Node node : root) {
            deque.add(node);
            while (!deque.isEmpty()) {
                Node poll = deque.poll();
                List<Node> childNodes = AtlasUtil.getSubNodeAccordId(map.getLinks(), poll.getId(), hashMap);
                if (childNodes.size() > 0) {
                    int floor = poll.getFloor();
                    for (Node ch : childNodes) {
                        if (null != ch) {
                            ch.setFloor(floor + 1);
                            deque.push(ch);
                        }
                    }
                }
            }
        }
        return bean;
    }

    public static CanvasBean getCanvasAccordAtlas(AtlasBean atlasBean) {
        CanvasBean bean = new CanvasBean();
        List<Node> org = atlasBean.getData().getMapping().getNodes();
        for (Node node : org) {
            if (node.getX() > bean.getEndX()) {
                bean.setEndX(node.getX());
            }

            if (node.getX() < bean.getStartX()) {
                bean.setStartX(node.getX());
            }

            if (node.getY() > bean.getEndY()) {
                bean.setEndY(node.getY());
            }

            if (node.getY() < bean.getStartY()) {
                bean.setStartY(node.getY());
            }
        }

        bean.setWidth((bean.getEndX() - bean.getStartX()));
        bean.setHeight(bean.getEndY() - bean.getStartY());
        return bean;
    }

    public static CanvasBean getCanvasAccordAtlas(AtlasMapping mapping) {
        CanvasBean bean = new CanvasBean();
        List<Node> org = mapping.getNodes();
        for (Node node : org) {
            /*if (!node.isVisibility()) {
                continue;
            }*/
            if (node.getX() > bean.getEndX()) {
                bean.setEndX(node.getX());
            }

            if (node.getX() < bean.getStartX()) {
                bean.setStartX(node.getX());
            }

            if (node.getY() > bean.getEndY()) {
                bean.setEndY(node.getY());
            }

            if (node.getY() < bean.getStartY()) {
                bean.setStartY(node.getY());
            }
        }

        bean.setWidth((bean.getEndX() - bean.getStartX()));
        bean.setHeight(bean.getEndY() - bean.getStartY());
        return bean;
    }


    private static LinkedHashMap<Long, Node> getNodeList(AtlasMapping mapping) {
        LinkedHashMap<Long, Node> map = new LinkedHashMap<>();
        List<Node> nodes = mapping.getNodes();
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
            model.setIs_study(node.isIs_study());
            model.setOrder(AtlasUtil.getOrderInNodes(mapping, node.getId()) + 1);
            if (node.getType() == 1) {
                model.setVisibility(true);
            }
            map.put(node.getId(), model);
        }
        return map;
    }

    public static LinkedHashMap<Long, Node> updateNodeList(AtlasMapping mapping) {
        LinkedHashMap<Long, Node> map = new LinkedHashMap<>();
        List<Node> nodes = mapping.getNodes();
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
            model.setIs_study(node.isIs_study());
            model.setOrder(AtlasUtil.getOrderInNodes(mapping, node.getId()) + 1);
            model.setVisibility(node.isVisibility());
            map.put(node.getId(), model);
        }
        return map;
    }


    public static List<Long> getVisibleCanvasAccordAtlas(AtlasMapping mapping) {
        LinkedHashMap<Long, Node> modes = getNodeList(mapping);
        HashMap<Long, Long[]> ktmap = mapping.getKtmap();
        List<Long> visibleNodeId = new ArrayList<>();
        inner:
        for (Map.Entry<Long, Long[]> integerEntry : ktmap.entrySet()) {
            visibleNodeId.add(integerEntry.getKey());
            if (modes.get(integerEntry.getKey()) != null && (modes.get(integerEntry.getKey())).isIs_study()) {
                visibleNodeId.addAll(Arrays.asList(integerEntry.getValue()));
                continue;
            }

            for (Long aLong : integerEntry.getValue()) {
                if (modes.get(aLong) != null && (modes.get(aLong)).isIs_study()) {
                    visibleNodeId.addAll(Arrays.asList(integerEntry.getValue()));
                    continue inner;
                }
            }
        }
        return visibleNodeId;
    }

    public static void updateVisibleMapping(AtlasMapping mapping, List<Long> visibleNodeId) {
        for (Node node : mapping.getNodes()) {
            if (visibleNodeId.contains(node.getId())) {
                node.setVisibility(true);
            }
        }
    }


    public static String replace(String str) {
        String destination = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            destination = m.replaceAll("");
        }
        return destination;
    }


    public static void adapterViewSize(TreeView editMapTreeView) {
        editMapTreeView.post(new Runnable() {
            @Override
            public void run() {
                int childHeight = editMapTreeView.getHeight();
                int childWidth = editMapTreeView.getWidth();
                ViewGroup viewGroup = (ViewGroup) editMapTreeView.getParent();
                int parentWidth = viewGroup.getWidth();
                int parentHeight = viewGroup.getHeight();

                float min = Math.min((float) parentHeight / childHeight, (float) parentWidth / childWidth);
                if (min >= 1.6f) {
                    min = 1.6f;
                }

                if (min <= 1f) {
                    if (min <= 0.4f) {
                        min = 0.4f;
                    }
                    float index = (1.0f - min) / 0.05f;
                    BigDecimal b = new BigDecimal(index).setScale(0, BigDecimal.ROUND_HALF_UP);

                    index = b.intValue();
                    editMapTreeView.getmMoveAndScaleHandler().setCurIndex((12 - (int) index));
                    editMapTreeView.setScaleX(min);
                    editMapTreeView.setScaleY(min);

                    return;
                }


                float index = (min - 1.0f) / 0.05f;
                BigDecimal b = new BigDecimal(index).setScale(0, BigDecimal.ROUND_HALF_UP);

                index = b.intValue();
                editMapTreeView.getmMoveAndScaleHandler().setCurIndex((12 + (int) index));
                editMapTreeView.setScaleX(min);
                editMapTreeView.setScaleY(min);
            }
        });
    }

}
