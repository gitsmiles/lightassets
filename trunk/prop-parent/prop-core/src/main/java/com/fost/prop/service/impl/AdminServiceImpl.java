package com.fost.prop.service.impl;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fost.prop.annotation.Log;
import com.fost.prop.api.model.Attribute;
import com.fost.prop.api.model.Node;
import com.fost.prop.dao.AttributeDao;
import com.fost.prop.dao.NodeDao;
import com.fost.prop.model.Operator;
import com.fost.prop.service.AdminService;
import com.fost.prop.service.TreeService;
import com.fost.prop.service.XMLService;

public class AdminServiceImpl implements AdminService {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	private AttributeDao attributeDao;

	private NodeDao nodeDao;

	private XMLService xmlService;

	private TreeService treeService;

	public void setAttributeDao(AttributeDao attributeDao) {
		this.attributeDao = attributeDao;
	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public void setXmlService(XMLService xmlService) {
		this.xmlService = xmlService;
	}

	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}

	@Transactional
	@Log(event = "add", table = "node")
	public Node addNode(Node node, Operator op) {
		List<Node> list = nodeDao.findNodesByParentId(node.getParentId());
		for (Node n : list) {
			if (n.getName().equalsIgnoreCase(node.getName())) {
				LOGGER.info("the node's name has bean used in the same parent!");
				return null;
			}
		}

		Node parent = nodeDao.findNode(node.getParentId());
		if (parent.getParentId() != -1) {// 如果当前节点不是一个应用的根节点
			node.setAppId(parent.getAppId());
		} else {// 如果当前节点是一个应用的根节点,那么要将rootId设置为自己的ID
			int id = nodeDao.getNodeId();
			node.setId(id);
			node.setAppId(id);
		}
		nodeDao.updateVersion(node.getAppId());

		return nodeDao.insertNode(node);
	}

	@Override
	@Transactional
	@Log(event = "add", table = "attribute")
	public Object addAttribute(Attribute attr, Operator op) {
		// 防止一个节点的属性名字相同
		List<Attribute> list = attributeDao.findAttributesByNodeId(attr.getNodeId());
		for (Attribute a : list) {
			if (a.getKey().equalsIgnoreCase(attr.getKey())) {
				LOGGER.info("the attribute's name has bean used!");
				return null;
			}
		}

		Node node = nodeDao.findNode(attr.getNodeId());
		attr.setAppId(node.getAppId());
		nodeDao.updateVersion(node.getAppId());
		return attributeDao.insertAttribute(attr);
	}

	@Override
	public List<Attribute> findAttributesByNodeId(int nodeId) {
		return attributeDao.findAttributesByNodeId(nodeId);
	}

	// @Transactional

	@Override
	public List<Node> findNodesByParentId(int nodeId) {
		return nodeDao.findNodesByParentId(nodeId);
	}

	@Override
	@Transactional
	@Log(event = "delete", table = "attribute")
	public int deleteAttributeById(int attributeId, Operator op) {
		Attribute attr = attributeDao.findAttribute(attributeId);
		nodeDao.updateVersion(attr.getAppId());

		return attributeDao.deleteAttribute(attributeId);
	}

	@Override
	@Transactional
	@Log(event = "delete", table = "node")
	public int deleteNodeById(int nodeId, Operator op) {
		List<Node> list = nodeDao.findNodesByParentId(nodeId);
		if (list.size() > 0) {
			LOGGER.info("this node has childern node!");
			return 0;
		}

		Node node = nodeDao.findNode(nodeId);
		if (node.getParentId() != -1) {// 只要不是虚根节点,就修改应用根节点的版本号
			nodeDao.updateVersion(node.getAppId());
		}

		attributeDao.deleteAttributesByNodeId(nodeId);

		return nodeDao.deleteNode(nodeId);
	}

	@Override
	public Node findNodeById(int nodeId) {
		return nodeDao.findNode(nodeId);
	}

	@Override
	@Transactional
	@Log(event = "update", table = "attribute")
	public int updateAttribute(Attribute attr, Operator op) {
		List<Attribute> list = attributeDao.findAttributesByNodeId(attr.getNodeId());

		for (Attribute a : list) {
			if (a.getKey().equalsIgnoreCase(attr.getKey())) {
				if (a.getId() != attr.getId()) {
					LOGGER.info("the attribute's name has bean used!");
					return 0;
				}
			}
		}

		nodeDao.updateVersion(attr.getAppId()); // 更新应用版本号

		return attributeDao.updateAttribute(attr);
	}

	@Override
	@Transactional
	@Log(event = "update", table = "node")
	public int updateNode(Node node, Operator op) {
		List<Node> list = nodeDao.findNodesByParentId(node.getParentId());
		for (Node n : list) {
			if (n.getName().equalsIgnoreCase(node.getName())) {
				if (n.getId() != node.getId()) {
					LOGGER.info("the node's name has bean used!");
					return 0;
				}
			}
		}

		if (node.getParentId() != -1) {// 只要不是虚根节点
			nodeDao.updateVersion(node.getAppId());
		}
		return nodeDao.updateNode(node);
	}

	@Override
	public Attribute findAttributeById(int id) {
		return attributeDao.findAttribute(id);
	}

	public void moveUp(int nodeId, int attributeId) {
		Attribute attr1 = attributeDao.findAttribute(attributeId);
		Attribute attr2 = attributeDao.getUpId(attr1);
		if (attr2 == null)// 最上面了
			return;
		attributeDao.swapSortPosition(attr1, attr2);
		return;
	}

	public void moveDown(int nodeId, int attributeId) {
		Attribute attr1 = attributeDao.findAttribute(attributeId);
		Attribute attr2 = attributeDao.getDownId(attr1);
		if (attr2 == null)// 最下面了
			return;
		attributeDao.swapSortPosition(attr1, attr2);
		return;
	}

	/*
	 * 将xml的node内容导入数据库
	 */
	public void xml2database(int nodeId, File file, Operator op) {
		if (file == null)
			return;
		Node node = xmlService.createNodeFromXML(file);
		node.setParentId(nodeId);
		import2database(node);
	}

	/*
	 * 将数据库中的node节点导入xml
	 */
	public Document database2xml(int nodeId, Operator op) {
		Node node = treeService.loadTree(nodeId);
		return xmlService.createXMLFile(node);
	}

	/*
	 * 一定要保证传入的node是有父节点ID的
	 */
	@Transactional
	private void import2database(Node node) {
		if (node == null)
			return;

		node.setId(0);// 把节点的ID先干掉

		List<Node> childrenNode = node.getChildList();
		List<Attribute> attributes = node.getAttributeList();

		Node ret = nodeDao.insertNode(node); // 先把当前节点插入
		if (ret.getAppId() == 0) {// 根节点要设置appId 为 nodeId
			Node parent = nodeDao.findNode(ret.getParentId());
			if (parent.getParentId() == -1) {
				ret.setAppId(ret.getId());
			} else {
				ret.setAppId(parent.getAppId());
			}
			nodeDao.updateNode(ret);
			nodeDao.updateVersion(ret.getAppId());
		}

		if (attributes != null) {
			for (Attribute a : attributes) {// 把对应的属性节点插入
				a.setNodeId(ret.getId());// 设置父节点
				a.setId(0);// 把已经有的节点ID干掉
				a.setAppId(ret.getAppId());
				attributeDao.insertAttribute(a);
			}
		}

		if (childrenNode != null) {
			for (Node n : childrenNode) {
				n.setParentId(ret.getId());
				n.setAppId(ret.getAppId());
				
				import2database(n);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Method method = AdminServiceImpl.class.getMethod("addNode", new Class[] { Node.class });
		System.out.println(method.getAnnotation(Log.class));
	}

}
