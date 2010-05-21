package com.fost.prop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fost.prop.api.model.Node;
import com.fost.prop.service.AdminService;

public class PropertiesController {
    @Autowired
    @Qualifier("adminService")
    private AdminService adminService;

    private List<Node> childList;

    public List<Node> getChildList() {
        return childList;
    }

    public void setChildList(List<Node> childList) {
        this.childList = childList;
    }

    public String index() {
        this.childList = adminService.findNodesByParentId(-1);
        return "properties";
    }
}
