package com.readboy.atlasview.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AtlasMapping {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private long id;

    @SerializedName("links")
    private List<Link> links;

    @SerializedName("section")
    private Section section;

    @SerializedName("nodeOrder")
    private List<NodeOrder> nodeOrder;

    @SerializedName("nodes")
    private List<Node> nodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<NodeOrder> getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(List<NodeOrder> nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
