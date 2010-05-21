package com.fost.prop.api.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fost.common.BaseDataObject;

public class Node extends BaseDataObject {
    private static final long serialVersionUID = 1L;

    private int id;
    private int appId;
    private String name;
    private int version;
    private int parentId;
    private String createAt;
    private String updatedAt;

    private List<Attribute> attributeList;
    private List<Node> childList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public List<Node> getChildList() {
        return childList;
    }

    public void setChildList(List<Node> childList) {
        this.childList = childList;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toString(){
    	String result = super.toString();
    	return "[" + StringUtils.substringBetween(result, "[", "]") + "]";
    }    
}
