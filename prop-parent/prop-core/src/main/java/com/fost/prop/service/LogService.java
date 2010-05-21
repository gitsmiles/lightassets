package com.fost.prop.service;

import java.util.List;

import com.fost.common.dao.Page;
import com.fost.prop.model.OperationLog;

public interface LogService {
	public List<OperationLog> findLog(OperationLog log);
	public Page findLogWithPage(OperationLog log,Page page);
}
