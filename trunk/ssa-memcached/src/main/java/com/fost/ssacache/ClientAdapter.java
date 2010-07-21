/**
 * 
 */
package com.fost.ssacache;

/**
 * @author Janly
 *
 */
public interface ClientAdapter extends Cache{

	public abstract boolean isLocal();
	
	public abstract String getGroup();
}
