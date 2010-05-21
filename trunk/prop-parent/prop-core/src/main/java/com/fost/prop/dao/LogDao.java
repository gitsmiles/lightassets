package com.fost.prop.dao;

import java.util.List;

import com.fost.common.dao.Page;
import com.fost.prop.model.OperationLog;

public interface LogDao {
	public long insertLog(OperationLog log);
	public List<OperationLog> findLog(OperationLog log);
	public Page findLogWithPage(OperationLog log,Page page);
}
