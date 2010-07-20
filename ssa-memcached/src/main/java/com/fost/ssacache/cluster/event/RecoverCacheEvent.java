/**
 * 
 */
package com.fost.ssacache.cluster.event;

import com.fost.ssacache.cluster.EventEnum;


/**
 * @author Janly
 *
 */
public class RecoverCacheEvent extends AbstractCacheEvent{

	private int exp;
	private Object value;
	
	@Override
	public EventEnum getEventEnum() {
		return EventEnum.recover;
	}

	public final int getExp() {
		return exp;
	}

	public final void setExp(int exp) {
		this.exp = exp;
	}

	public final Object getValue() {
		return value;
	}

	public final void setValue(Object value) {
		this.value = value;
	}
	
}
