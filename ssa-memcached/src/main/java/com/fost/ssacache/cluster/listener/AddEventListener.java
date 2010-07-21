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
import com.fost.ssacache.cluster.event.AddCacheEvent;

/**
 * @author Janly
 *
 */
public class AddEventListener implements EventListener {

	@Override
	public boolean captureEvent(CacheEvent cacheEvent) {
		if(EventEnum.add.equals(cacheEvent.getEventEnum())) return true;
		return false;
	}


	@Override
	public void executeEvent(CacheEvent cacheEvent, boolean syn) throws TimeoutException,InterruptedException{
		AddCacheEvent event=(AddCacheEvent)cacheEvent;
		String group=ClusterUtil.getMasterClientAdapter(cacheEvent.getEventManager().getGroupAdapterMap(), event.getKey()).getGroup();
		java.util.List<ClientAdapter> clients=cacheEvent.getEventManager().getGroupAdapterMap().get(group);

		for(ClientAdapter client:clients){
			if(syn){
				client.add(event.getKey(), event.getExp(), event.getValue(), event.getTimeout());
			}else{
				try{
					client.addWithNoReply(event.getKey(), event.getExp(), event.getValue());	
				}catch(Exception e){
					
				}
				
			}
		}
		
	}

}
