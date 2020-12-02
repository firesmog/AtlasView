package com.readboy.atlasview.bean;

import java.util.List;

public class AtlasMapping {
    private String name;
    private long id;
    private List<Link> links;
    private Section section;
    private List<NodeOrder> nodeOrder;
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

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "AtlasMapping{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", links=" + links +
                ", section=" + section +
                ", nodeOrder=" + nodeOrder +
                ", nodes=" + nodes +
                '}';
    }
}
