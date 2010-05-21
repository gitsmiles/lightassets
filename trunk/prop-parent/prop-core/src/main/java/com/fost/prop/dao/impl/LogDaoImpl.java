package com.fost.prop.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fost.common.dao.Page;
import com.fost.prop.dao.LogDao;
import com.fost.prop.model.OperationLog;

@Repository("logDao")
public class LogDaoImpl extends BaseDao implements LogDao{
	@Override
	public long insertLog(OperationLog log){
		return (Long)getSqlMapClientTemplate().insert("LogDao.insertLog", log);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationLog> findLog(OperationLog log) {
		return getSqlMapClientTemplate().queryForList("LogDao.findLog", log);
	}

	public Page findLogWithPage(OperationLog log,Page page){
		page.setSqlmapParameters(tranObj2Map(log));
		page.setStatementName("LogDao.findLogWithPage");
		page.setSumStatementName("LogDao.findLogSum");
		return getPaginated(page);
	}
}
