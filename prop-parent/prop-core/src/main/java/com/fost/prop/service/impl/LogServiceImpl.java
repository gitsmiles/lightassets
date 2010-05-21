package com.fost.prop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fost.common.dao.Page;
import com.fost.prop.dao.LogDao;
import com.fost.prop.model.OperationLog;

@Service("logService")
public class LogServiceImpl implements com.fost.prop.service.LogService {
	@Autowired
	private LogDao logDao;

	@Override
	public List<OperationLog> findLog(OperationLog log) {
		return logDao.findLog(log);
	}
	
	public Page findLogWithPage(OperationLog log,Page page){
		return logDao.findLogWithPage(log, page);
	}
}
