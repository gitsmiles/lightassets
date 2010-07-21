package com.fost.ssacache.impl;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.Cache;
import com.fost.ssacache.ClientAdapter;
import com.fost.ssacache.cluster.ClusterEnum;
import com.fost.ssacache.cluster.ClusterUtil;
import com.fost.ssacache.cluster.EventManager;
import com.fost.ssacache.cluster.event.AddCacheEvent;
import com.fost.ssacache.cluster.event.RecoverCacheEvent;
/**
 * 
 * @author Janly
 *
 */
public class ClusterCache implements Cache{
	private java.util.List<ClientAdapter> adapters;
	private java.util.Map<String, java.util.List<ClientAdapter>> groupAdapterMap;
	private ClusterEnum mode;
	
	public ClusterCache(String value){
		mode=ClusterEnum.valueOf(value);
		this.adapters=java.util.Collections.synchronizedList(new java.util.LinkedList<ClientAdapter>());
		this.groupAdapterMap=new java.util.concurrent.ConcurrentHashMap<String, java.util.List<ClientAdapter>>(1);
	}
	

	
	@Override
	public boolean add(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		switch (mode){
		case sticky:
			return this.getMasterClientAdapter(key).add(key, exp, value, timeout);
		case active:
		case standby:
			AddCacheEvent event=new AddCacheEvent();
			event.setKey(key);
			event.setValue(value);
			event.setTimeout(timeout);
			EventManager.getInstance().publishEvent(event);
			return true;
		}
		
		return false;
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		switch (mode){
			case sticky:
				this.getMasterClientAdapter(key).addWithNoReply(key, exp, value);
				break;
			case active:
			case standby:
				AddCacheEvent event=new AddCacheEvent();
				event.setKey(key);
				event.setValue(value);
				EventManager.getInstance().publishEvent(event);
		}
		
		
	}

	@Override
	public boolean delete(String key, int time) throws TimeoutException,
			InterruptedException {
		switch (mode){
		case sticky:
			return this.getMasterClientAdapter(key).delete(key, time);
		case active:
			
			break;
		case standby:
			
			break;
	
		}
		return false;
		
	}

	@Override
	public void deleteWithNoReply(String key) throws InterruptedException {
		switch (mode){
		case sticky:
			this.getMasterClientAdapter(key).deleteWithNoReply(key);
			break;
		case active:
			
			break;
		case standby:
			
			break;
	
		}
		
	}

	@Override
	public Object get(String key, long timeout) throws TimeoutException,
			InterruptedException {
		Object obj=null;
		switch (mode){
		case local:
			java.lang.StringBuilder sb=new java.lang.StringBuilder();
			int len=this.adapters.size();
			for(int i=0;i<len;i++){
				ClientAdapter client=this.adapters.get(i);
				if(client.isLocal()) {
					obj=client.get(key, timeout);
					if(obj!=null) return obj;
					if(i+1!=len) sb.append(i+",");
					if(i+1==len) sb.append(i);
				}
			}
			
			String[] idx=sb.toString().split(",");
			for(int i=0;i<idx.length;i++){
				obj=this.adapters.get(i);
				if(obj!=null) return obj;
			}
			break;
		case sticky:
			return this.getMasterClientAdapter(key).get(key, timeout);
		case active:
			String group=this.getMasterClientAdapter(key).getGroup();
			for(ClientAdapter client:this.adapters){
				if(!group.equals(client.getGroup())) continue;
				obj=client.get(key, timeout);
				if(obj!=null) {
					RecoverCacheEvent event=new RecoverCacheEvent();
					event.setKey(key);
					event.setValue(obj);
					EventManager.getInstance().publishEvent(event);
					return obj;
				
				}
			}
			break;
		case standby:
			group=this.getMasterClientAdapter(key).getGroup();
			for(ClientAdapter client:this.adapters){
				if(!group.equals(client.getGroup())) continue;
				obj=client.get(key, timeout);
				if(obj!=null) return obj;
			}
			break;
		}
		return null;
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout) throws TimeoutException, InterruptedException {
		
		switch (mode){
		case sticky:
			this.getMasterClientAdapter(key).set(key, exp, value, timeout);
			break;
		case active:
			
			break;
		case standby:
			
			break;
	
		}
		return false;
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		
		switch (mode){
		case sticky:
			this.getMasterClientAdapter(key).setWithNoReply(key, exp, value);
			break;
		case active:
			
			break;
		case standby:
			
			break;
	
		}
	}

	private ClientAdapter getMasterClientAdapter(String key){
		return ClusterUtil.getMasterClientAdapter(groupAdapterMap, key);
	}
	
	
	public void addClientAdapter(ClientAdapter clientAdapter){
		this.adapters.add(clientAdapter);
		java.util.List<ClientAdapter> list=this.groupAdapterMap.get(clientAdapter.getGroup());
		if(list==null) list=java.util.Collections.synchronizedList(new java.util.LinkedList<ClientAdapter>());
		list.add(clientAdapter);
		this.groupAdapterMap.put(clientAdapter.getGroup(), list);
	}
	
}
