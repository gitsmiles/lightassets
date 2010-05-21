package com.fost.prop.dao.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.fost.common.dao.Page;

public class BaseDao {
    private SqlMapClientTemplate sqlMapClientTemplate;

    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMapClientTemplate;
    }

    @Autowired
    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }

	/**
	 * 分页查询 <br>
	 * 通过Page.getPageList()来获取当前页面要展示的数据列表
	 * 
	 * @param pageinfo
	 * @return
	 * 
	 */
	public Page getPaginated(Page pageinfo) {
		// 查询数据statement name
		String statementName = pageinfo.getStatementName();
		String sumStatementName = pageinfo.getSumStatementName();
		// 页数
		if (pageinfo.getCurrentPage() <= 0) {
			pageinfo.setCurrentPage(1);
		}
		int currentPage = pageinfo.getCurrentPage();
		int pageSize = pageinfo.getPageSize();
		// 开始,结束行
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = (currentPage - 1) * pageSize + pageSize;
		if (startRow < 0) {
			startRow = 0;
		}
		if (endRow <= 0) {
			endRow = 1;
		}

		pageinfo.getSqlmapParameters().put("startRow", startRow);
		pageinfo.getSqlmapParameters().put("endRow", endRow);

		int recordSum = (Integer) getSqlMapClientTemplate().queryForObject(sumStatementName,
				pageinfo.getSqlmapParameters());
		pageinfo.setRecordSum(recordSum);
		pageinfo.setPageList(getListByStatementName(statementName, pageinfo.getSqlmapParameters()));

		return pageinfo;
	}

	/**
	 * 转相应的值对象转换成MAP的键值对
	 * 
	 * @param record
	 * @return
	 */
	protected Map<String, Object> tranObj2Map(Object record) {
		Map<String, Object> param = new HashMap<String, Object>();
		Field[] fields = record.getClass().getDeclaredFields();
		Object val;
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				val = fields[i].get(record);
				if (val != null && !val.toString().equals("")) {
					// param.put(fields[i].getName(), val.toString());
					param.put(fields[i].getName(), val);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("fetch field error..");
		}
		return param;
	}

	@SuppressWarnings("unchecked")
	public List getListByStatementName(String statementName, Object parameter) {
		return getSqlMapClientTemplate().queryForList(statementName, parameter);
	}
    
}
