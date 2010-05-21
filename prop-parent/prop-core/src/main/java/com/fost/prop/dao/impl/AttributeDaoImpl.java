package com.fost.prop.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fost.prop.api.model.Attribute;
import com.fost.prop.dao.AttributeDao;

@Repository("attributeDao")
public class AttributeDaoImpl extends BaseDao implements AttributeDao {
    @Override
    public int deleteAttribute(int id) {
        return getSqlMapClientTemplate().delete("AttributeDao.deleteAttribute", Integer.valueOf(id));
    }

    @Override
    public Attribute findAttribute(int id) {
        return (Attribute) getSqlMapClientTemplate().queryForObject("AttributeDao.findAttribute", Integer.valueOf(id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Attribute> findAttributesByNodeId(int nodeId) {
        return getSqlMapClientTemplate().queryForList("AttributeDao.findAttributesByNodeId", Integer.valueOf(nodeId));
    }

    @Override
    public Attribute insertAttribute(Attribute attribute) {
        int maxId = 0;
        try {
            maxId = getMaxSortId() + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        attribute.setId(getAttributeId());
        attribute.setSorting(maxId);

        getSqlMapClientTemplate().insert("AttributeDao.insertAttribute", attribute);
        return attribute;
    }

    @Override
    public int updateAttribute(Attribute attribute) {
        return getSqlMapClientTemplate().update("AttributeDao.updateAttribute", attribute);
    }

    public int deleteAttributesByNodeId(int nodeId) {
        return getSqlMapClientTemplate().delete("AttributeDao.deleteAttributesByNodeId", nodeId);
    }

    @Override
    public int getMaxSortId() throws Exception {
        int maxId = 0;
        Object obj = null;
        try {
            obj = getSqlMapClientTemplate().queryForObject("AttributeDao.getMaxSorting");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        if (obj != null) {
            maxId = ((Integer) obj).intValue();
        }
        return maxId;
    }

    @Override
    public void swapSortPosition(Attribute attr1, Attribute attr2) {
        int temp = 0;
        temp = attr1.getSorting();
        attr1.setSorting(attr2.getSorting());
        attr2.setSorting(temp);

        updateAttribute(attr1);
        updateAttribute(attr2);
    }

    public Attribute getUpId(Attribute attr) {
        return (Attribute) getSqlMapClientTemplate().queryForObject("AttributeDao.getUpID", attr);
    }

    public Attribute getDownId(Attribute attr) {
        return (Attribute) getSqlMapClientTemplate().queryForObject("AttributeDao.getDownID", attr);
    }

    protected int getAttributeId() {
        return (Integer) getSqlMapClientTemplate().queryForObject("AttributeDao.getAttributeId");
    }

    @SuppressWarnings("unchecked")
    public List<Attribute> findAll() {
        return getSqlMapClientTemplate().queryForList("AttributeDao.findAll");
    }
}
