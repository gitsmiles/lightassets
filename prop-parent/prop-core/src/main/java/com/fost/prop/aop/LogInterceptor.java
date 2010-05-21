package com.fost.prop.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;

import com.fost.prop.annotation.Log;
import com.fost.prop.api.model.Attribute;
import com.fost.prop.api.model.Node;
import com.fost.prop.dao.AttributeDao;
import com.fost.prop.dao.LogDao;
import com.fost.prop.dao.NodeDao;
import com.fost.prop.dao.impl.AttributeDaoImpl;
import com.fost.prop.dao.impl.LogDaoImpl;
import com.fost.prop.dao.impl.NodeDaoImpl;
import com.fost.prop.model.Operator;
import com.fost.prop.model.OperationLog;

public class LogInterceptor implements MethodInterceptor {
    @Autowired
	private NodeDao nodeDao;
    @Autowired
	private AttributeDao attributeDao;
    @Autowired
	private LogDao logDao;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Class myclass = ClassUtils.getUserClass(invocation.getThis().getClass());

		Method mt = myclass.getMethod(method.getName(), method.getParameterTypes());
		
		Object[] arguments = invocation.getArguments();

		Node oldNode = null;
		Attribute oldAttribute = null;
		Log logAnnotation = null;
		if (mt.isAnnotationPresent(Log.class)) {
			logAnnotation = mt.getAnnotation(Log.class);
			if(logAnnotation.event().equalsIgnoreCase("delete")){
				for (Object o : arguments){
					if(o instanceof Integer){
						if(logAnnotation.table().equalsIgnoreCase("node")){
							oldNode = nodeDao.findNode(((Integer) o).intValue());
						} else if(logAnnotation.table().equalsIgnoreCase("attribute")){
							oldAttribute = attributeDao.findAttribute(((Integer) o).intValue());
						}
						break;
					}
				}
			} else if(logAnnotation.event().equalsIgnoreCase("update")){
				for (Object o : arguments){
				    if(logAnnotation.table().equalsIgnoreCase("node")){
				    	if(o instanceof Node){
				    		oldNode = nodeDao.findNode(((Node) o).getId());
				    		break;
				    	}
					} else if(logAnnotation.table().equalsIgnoreCase("attribute")){
						if(o instanceof Attribute){
							oldAttribute = attributeDao.findAttribute(((Attribute) o).getId());
							break;
						}
					}	
				}
			}
		}

		Object obj = invocation.proceed();
	
		if(obj == null){
			return obj;
		}else{
			if(obj instanceof Integer){
				if(((Integer)obj).intValue() < 1){
					return obj;
				}
			}
		}
		
		if (mt.isAnnotationPresent(Log.class)) {
			logAnnotation = mt.getAnnotation(Log.class);

			OperationLog opLog = new OperationLog();
			
			opLog.setEvent(logAnnotation.event());

			if (logAnnotation.table().equalsIgnoreCase("node")) {
				if(logAnnotation.event().equalsIgnoreCase("add")){
					for (Object o : arguments) {
						if (o instanceof Node) {
							opLog.setNewContent(((Node) o).toString());
						} else if (o instanceof Operator) {
							opLog.setOperatorId(((Operator) o).getId());
							opLog.setOperatorName(((Operator) o).getName());
						}
					}
				}else if(logAnnotation.event().equalsIgnoreCase("delete")){
					for (Object o : arguments) {
						if (o instanceof Integer) {
							if(oldNode != null){// there is no 'oldcontent' ,when we invoke add method
								opLog.setOldContent(oldNode.toString());
							}
						} else if (o instanceof Operator) {
							opLog.setOperatorId(((Operator) o).getId());
							opLog.setOperatorName(((Operator) o).getName());
						}
					}
				}else if(logAnnotation.event().equalsIgnoreCase("update")){
					for (Object o : arguments) {
						if (o instanceof Node) {
							if(oldNode != null){// there is no 'oldcontent' ,when we invoke add method
								opLog.setOldContent(oldNode.toString());
							}
							opLog.setNewContent(((Node) o).toString());
						} else if (o instanceof Operator) {
							opLog.setOperatorId(((Operator) o).getId());
							opLog.setOperatorName(((Operator) o).getName());
						}
					}
				}else if(logAnnotation.event().equalsIgnoreCase("import")){
					
				}else if(logAnnotation.event().equalsIgnoreCase("export")){
					
				}
			} else if (logAnnotation.table().equalsIgnoreCase("attribute")) {
				if(logAnnotation.event().equalsIgnoreCase("add")){
					for (Object o : arguments) {
						if (o instanceof Attribute) {
							opLog.setNewContent(((Attribute) o).toString());
						} else if (o instanceof Operator) {
							opLog.setOperatorId(((Operator) o).getId());
							opLog.setOperatorName(((Operator) o).getName());
						}
					}
				}else if(logAnnotation.event().equalsIgnoreCase("delete")){
					for (Object o : arguments) {
						if (o instanceof Integer) {
							if(oldAttribute != null){// there is no 'oldcontent' ,when we invoke add method
								opLog.setOldContent(oldAttribute.toString());
							}
						} else if (o instanceof Operator) {
							opLog.setOperatorId(((Operator) o).getId());
							opLog.setOperatorName(((Operator) o).getName());
						}
					}					
				}else if(logAnnotation.event().equalsIgnoreCase("update")){
					for (Object o : arguments) {
						if (o instanceof Attribute) {
							if(oldAttribute != null){// there is no 'oldcontent' ,when we invoke add method
								opLog.setOldContent(oldAttribute.toString());
							}
							opLog.setNewContent(((Attribute) o).toString());
						} else if (o instanceof Operator) {
							opLog.setOperatorId(((Operator) o).getId());
							opLog.setOperatorName(((Operator) o).getName());
						}
					}					
				}else if(logAnnotation.event().equalsIgnoreCase("import")){
					
				}else if(logAnnotation.event().equalsIgnoreCase("export")){
				
				}
			}
			// insert log to database;
			try{
				logDao.insertLog(opLog);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return obj;
	}
}
