package com.fost.prop.web.controller;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fost.prop.api.model.Attribute;
import com.fost.prop.service.AdminService;

@Results(@Result(name = "success", type = "redirect", location = "item-list?nodeId=${nodeId}"))
public class AttributeController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AttributeController.class);
    @Autowired
    @Qualifier("adminService")
    AdminService adminService;

    private Integer id;
    private String key;
    private String value;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private Integer sorting;
    private Integer nodeId;
    private Integer attributeId;
    private String message;
    private String memo;

    public String editNew() {
        return "addAttribute";
    }

    public String create() {
        if (!validat()) {
            return "paramnull";
        }
        Attribute attribute = new Attribute();
        attribute.setKey(key);
        attribute.setValue(value);
        attribute.setValue1(value1);
        attribute.setValue2(value2);
        attribute.setValue3(value3);
        attribute.setValue4(value4);
        attribute.setValue5(value5);
        attribute.setNodeId(nodeId);
        attribute.setMemo(memo);
        attribute.setSorting(0);

        adminService.addAttribute(attribute, opuser);

        return "success";
    }

    public String destroy() {
        adminService.deleteAttributeById(id, opuser);
        return "success";
    }

    public String edit() {
        Attribute attribute = adminService.findAttributeById(id);
        key = attribute.getKey();
        value = attribute.getValue();
        value1 = attribute.getValue1();
        value2 = attribute.getValue2();
        value3 = attribute.getValue3();
        value4 = attribute.getValue4();
        value5 = attribute.getValue5();
        nodeId = attribute.getNodeId();
        memo = attribute.getMemo();
        sorting = attribute.getSorting();
        return "edit";
    }

    public String update() {
        if (!validat()) {
            return "paramnull";
        }
        Attribute attribute = adminService.findAttributeById(id);
        attribute.setKey(key);
        attribute.setValue(value);
        attribute.setValue1(value1);
        attribute.setValue2(value2);
        attribute.setValue3(value3);
        attribute.setValue4(value4);
        attribute.setValue5(value5);
        attribute.setMemo(memo);
        attribute.setSorting(sorting);
        attribute.setNodeId(nodeId);

        adminService.updateAttribute(attribute, opuser);

        return "success";
    }

    public String moveUp() {
        try {
            adminService.moveUp(nodeId, attributeId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return "success";
    }

    public String moveDown() {
        try {
            adminService.moveDown(nodeId, attributeId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return "success";
    }

    public boolean validat() {
        if (key == null || key.equals("")) {
            this.message = "the attribute's name cannot be null!";
            return false;
        } else if (key.length() > 255) {
            this.message = "the attribute's key is too long,it must be less than 255 characters";
            return false;
        }
        if (value == null || value.equals("")) {
            this.message = "the attribute's value cannot be null!";
            return false;
        } else if (value.length() > 4000) {
            this.message = "the attribute's memo is too long,it must be less than 4000 characters";
            return false;
        }
        if (memo != null && memo.length() > 200) {
            this.message = "the attribute's memo is too long,it must be less than 200 characters";
            return false;
        }
        if (nodeId == 0) {
            this.message = "the attribute's parent cannot be null!";
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    public String getValue5() {
        return value5;
    }

    public void setValue5(String value5) {
        this.value5 = value5;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
