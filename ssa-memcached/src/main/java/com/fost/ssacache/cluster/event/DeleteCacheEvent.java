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
	private long timeout;
	
	@Override
	public EventEnum getEventEnum() {
		return EventEnum.delete;
	}


	public final long getTimeout() {
		return timeout;
	}

	public final void setTimeout(long timeout) {
		this.timeout = timeout;
	}


	
	
}
