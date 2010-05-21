package com.fost.prop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fost.prop.api.model.Node;
import com.fost.prop.service.AdminService;

public class AppentController {
    @Autowired
    @Qualifier("adminService")
    private AdminService adminService;

    private List<Node> childList;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Node> getChildList() {
        return childList;
    }

    public void setChildList(List<Node> childList) {
        this.childList = childList;
    }

    public String index() {
        this.childList = adminService.findNodesByParentId(id);
        return "jump";
    }
}
