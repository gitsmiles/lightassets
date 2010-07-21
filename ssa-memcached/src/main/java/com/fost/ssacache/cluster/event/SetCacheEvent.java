package com.fost.ssacache.cluster.event;

import com.fost.ssacache.cluster.EventEnum;

/**
 * @author Janly
 *
 */
public class SetCacheEvent extends AddCacheEvent{

	@Override
	public EventEnum getEventEnum() {
		return EventEnum.set;
	}
}
