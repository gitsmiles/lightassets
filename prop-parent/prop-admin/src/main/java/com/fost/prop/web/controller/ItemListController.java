package com.fost.prop.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fost.prop.api.model.Attribute;
import com.fost.prop.api.model.Node;
import com.fost.prop.service.AdminService;

@Results(@Result(name = "success", type = "redirectAction", params = { "actionName", "itemList" }))
public class ItemListController extends BaseController {
	private final static Logger LOGGER = LoggerFactory.getLogger(ItemListController.class);
	@Autowired
	@Qualifier("adminService")
	private AdminService adminService;

	private List<Attribute> attrList;
	private int nodeId;
	private String currPath;

	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	public String index() {
		this.attrList = adminService.findAttributesByNodeId(nodeId);
		currPath = setPath(nodeId);
		return "itemlist";
	}

	private String setPath(int nodeId) {
		StringBuffer temp = new StringBuffer();
		List<String> list = new ArrayList<String>();
		Node node = adminService.findNodeById(nodeId);

		while (node != null && node.getParentId() != -1) {
			list.add(node.getName());
			node = adminService.findNodeById(node.getParentId());
		}

		int i = list.size() - 1;
		for (; i >= 0; i--) {
			temp.append(list.get(i));
			if (i != 0)
				temp.append("/");
		}

		return temp.toString();
	}

	/*
	 * 将节点数据写入xml文件，给客户端下载
	 */
	public String downLoadFile() {
		int result = 0;

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/octet-stream");

		String fileName = adminService.findNodeById(nodeId).getName() + ".xml";

		try {
			fileName = new String(fileName.getBytes(), "ISO8859-1");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		XMLWriter writer = null;

		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			writer = new XMLWriter(response.getOutputStream(), format);
			writer.write(adminService.database2xml(nodeId, opuser));

			result = 1;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

		if (result == 1) {
			return "dosuccess";
		}

		return "error";
	}

	/*
	 * 将上传文件的内容直接导入数据库
	 */
	public String uploadFile() {
		int result = 0;

		if (upload != null) {
			try {
				adminService.xml2database(nodeId, upload, opuser);
				result = 1;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

		if (result == 1) {
			return "dosuccess";
		}

		return "error";
	}

	public String getCurrPath() {
		return currPath;
	}

	public void setCurrPath(String currPath) {
		this.currPath = currPath;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public List<Attribute> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<Attribute> attrList) {
		this.attrList = attrList;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
}
