/**
 * 
 */
package com.fost.ssacache;

/**
 * @author Janly
 *
 */
public final class AnnotationContext {
	private String cacheName;
	private String namespace;
    private int expiration;
    private int timeOut;
    private String className;
    private String assignedKey;
    private boolean noreply;
    private boolean invoke;
    
	private java.util.Map<Integer, Integer> indexOrder=new java.util.HashMap<Integer, Integer>(0);
	private java.util.Map<Integer, String> indexName=new java.util.HashMap<Integer, String>(0);
	private java.util.Map<Integer, Object> indexObject=new java.util.HashMap<Integer, Object>(0);
	private java.util.Map<Integer, String> indexBeanName=new java.util.HashMap<Integer, String>(0);
    

	
	
	public final int getTimeOut() {
		return timeOut;
	}

	public final void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}


	public final java.util.Map<Integer, String> getIndexBeanName() {
		return indexBeanName;
	}

	public final java.util.Map<Integer, Integer> getIndexOrder() {
		return indexOrder;
	}

	public final java.util.Map<Integer, String> getIndexName() {
		return indexName;
	}

	public final java.util.Map<Integer, Object> getIndexObject() {
		return indexObject;
	}

	public final String getNamespace() {
		return namespace;
	}
	public final void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	
	public final int getExpiration() {
		return expiration;
	}
	public final void setExpiration(int expiration) {
		this.expiration = expiration;
	}
	public final String getClassName() {
		return className;
	}
	public final void setClassName(String className) {
		this.className = className;
	}
	public final String getAssignedKey() {
		return assignedKey;
	}
	public final void setAssignedKey(String assignedKey) {
		this.assignedKey = assignedKey;
	}


	

	public final boolean isNoreply() {
		return noreply;
	}

	public final void setNoreply(boolean noreply) {
		this.noreply = noreply;
	}

	public final boolean isInvoke() {
		return invoke;
	}

	public final void setInvoke(boolean invoke) {
		this.invoke = invoke;
	}

	public final String getCacheName() {
		return cacheName;
	}

	public final void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}


}
