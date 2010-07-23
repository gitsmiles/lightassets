/**
 * 
 */
package com.fost.ssacache.cluster.event;

import com.fost.ssacache.cluster.EventEnum;


/**
 * @author Janly
 *
 */
public class DeleteCacheEvent extends AbstractCacheEvent{
	private int timeout;
	
	@Override
	public EventEnum getEventEnum() {
		return EventEnum.delete;
	}


	public final int getTimeout() {
		return timeout;
	}

	public final void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	
	
}
