/**
 * 
 */
package com.fost.ssacache;

/**
 * @author Janly
 *
 */
public interface ClientAdapter extends Cache{
	
	public abstract int getWeight();
	
	public abstract void setWeight(int weight);

	public abstract void setClient(Object obj);
	
	public abstract void setLocal(boolean local);
	
	public abstract boolean isLocal();
	
	public abstract String getGroup();
	
	public abstract void setGroup(String group);
}
