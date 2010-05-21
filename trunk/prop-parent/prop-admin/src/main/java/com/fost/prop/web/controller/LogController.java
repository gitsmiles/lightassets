package com.fost.prop.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fost.common.dao.Page;
import com.fost.prop.model.OperationLog;
import com.fost.prop.service.LogService;
import com.fost.prop.util.DateUtils;
import com.opensymphony.xwork2.ModelDriven;

public class LogController implements ModelDriven<OperationLog> {

	@Autowired
	private LogService logService;

	private List<Long> dids = new ArrayList<Long>();

	private OperationLog model = new OperationLog();
	private List<OperationLog> logs;

	private int pager_offset;
	private Page page;

	public void setModel(OperationLog model) {
		this.model = model;
	}

	public OperationLog getModel() {
		return model;
	}

	public List<OperationLog> getLogs() {
		return logs;
	}

	public void setLogs(List<OperationLog> logs) {
		this.logs = logs;
	}

	public int getPager_offset() {
		return pager_offset;
	}

	public void setPager_offset(int pagerOffset) {
		pager_offset = pagerOffset;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Long> getDids() {
		return dids;
	}

	public void setDids(List<Long> dids) {
		this.dids = dids;
	}

	public String find() {
		page = new Page();
		page.setCurrentPage(pager_offset);
		model = new OperationLog();
		model.setBeginTime(DateUtils.getYesterDay());
		model.setEndTime(DateUtils.getToday());
		page = logService.findLogWithPage(model, page);
		return "logList";
	}

	public String findByModel() {
		page = new Page();
		page.setCurrentPage(pager_offset);
		page = logService.findLogWithPage(model, page);
		return "logList";
	}
	
	public String deletLog(){
		System.out.println(dids.size());
		return "dosuccess";
	}
}
