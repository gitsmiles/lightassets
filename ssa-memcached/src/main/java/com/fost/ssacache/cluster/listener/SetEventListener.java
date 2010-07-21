/**
 * 
 */
package com.fost.ssacache.cluster.listener;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.ClientAdapter;
import com.fost.ssacache.cluster.CacheEvent;
import com.fost.ssacache.cluster.ClusterUtil;
import com.fost.ssacache.cluster.EventEnum;
import com.fost.ssacache.cluster.EventListener;
import com.fost.ssacache.cluster.event.SetCacheEvent;

/**
 * @author Janly
 *
 */
public class SetEventListener implements EventListener {

	@Override
	public boolean captureEvent(CacheEvent cacheEvent) {
		if(EventEnum.set.equals(cacheEvent.getEventEnum())) return true;
		return false;
	}


	@Override
	public void executeEvent(CacheEvent cacheEvent, boolean syn) throws TimeoutException,InterruptedException{
		SetCacheEvent event=(SetCacheEvent)cacheEvent;
		String group=ClusterUtil.getMasterClientAdapter(cacheEvent.getEventManager().getGroupAdapterMap(), event.getKey()).getGroup();
		java.util.List<ClientAdapter> clients=cacheEvent.getEventManager().getGroupAdapterMap().get(group);

		for(ClientAdapter client:clients){
			if(syn){
				client.set(event.getKey(), event.getExp(), event.getValue(), event.getTimeout());
			}else{
				try{
					client.setWithNoReply(event.getKey(), event.getExp(), event.getValue());	
				}catch(Exception e){
					
				}
				
			}
		}
		
	}

}