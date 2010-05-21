package com.fost.prop.dao;

import java.util.List;

import com.fost.prop.api.model.Attribute;

public interface AttributeDao {
    Attribute insertAttribute(Attribute attribute);

    int updateAttribute(Attribute attribute);

    int deleteAttribute(int id);

    Attribute findAttribute(int id);

    List<Attribute> findAttributesByNodeId(int nodeId);

    int deleteAttributesByNodeId(int nodeId);

    int getMaxSortId() throws Exception;

    Attribute getUpId(Attribute attr);

    Attribute getDownId(Attribute attr);

    void swapSortPosition(Attribute attr1, Attribute attr2);

    List<Attribute> findAll();
}
