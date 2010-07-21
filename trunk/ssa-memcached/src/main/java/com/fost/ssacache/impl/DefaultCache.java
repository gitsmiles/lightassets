/**
 * 
 */
package com.fost.ssacache.impl;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.Cache;
import com.fost.ssacache.ClientAdapter;

/**
 * @author Janly
 *
 */
public class DefaultCache implements Cache{

	private java.util.List<ClientAdapter> localAdapters;
	private java.util.List<ClientAdapter> remoteAdapters;
	
	public DefaultCache(){
		this.localAdapters=java.util.Collections.synchronizedList(new java.util.LinkedList<ClientAdapter>());
		this.remoteAdapters=java.util.Collections.synchronizedList(new java.util.LinkedList<ClientAdapter>());
	}
	
	@Override
	public boolean add(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		if(this.existLocalCache()){
			this.getLocalClientAdapter(key).add(key, exp, value, timeout);
		}
		return this.getRemoteClientAdapter(key).add(key, exp, value, timeout);
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		if(this.existLocalCache()){
			this.getLocalClientAdapter(key).addWithNoReply(key, exp, value);
		}
		this.getRemoteClientAdapter(key).addWithNoReply(key, exp, value);
	}

	@Override
	public boolean delete(String key, int time) throws TimeoutException,
			InterruptedException {
		if(this.existLocalCache()){
			this.getLocalClientAdapter(key).delete(key, time);
		}
		return this.getRemoteClientAdapter(key).delete(key, time);
	}

	@Override
	public void deleteWithNoReply(String key) throws InterruptedException {
		if(this.existLocalCache()){
			this.getLocalClientAdapter(key).deleteWithNoReply(key);
		}
		this.getRemoteClientAdapter(key).deleteWithNoReply(key);
	}

	@Override
	public Object get(String key, long timeout) throws TimeoutException,
			InterruptedException {
		if(this.existLocalCache()){
			this.getLocalClientAdapter(key).get(key, timeout);
		}
		return this.getRemoteClientAdapter(key).get(key, timeout);
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		if(this.existLocalCache()){
			this.getLocalClientAdapter(key).set(key, exp, value, timeout);
		}
		return this.getRemoteClientAdapter(key).set(key, exp, value, timeout);
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		if(this.existLocalCache()){
			this.getLocalClientAdapter(key).setWithNoReply(key, exp, value);
		}
		this.getRemoteClientAdapter(key).setWithNoReply(key, exp, value);
		
	}
	
	
	private boolean existLocalCache(){
		if(this.localAdapters!=null&&this.localAdapters.size()>0) return true;
		return false;
	}
	
	private ClientAdapter getLocalClientAdapter(String key){
		int code=key.hashCode();
		int idx=code%this.localAdapters.size();
		return this.localAdapters.get(idx);
		
	}
	
	private ClientAdapter getRemoteClientAdapter(String key){
		int code=key.hashCode();
		int idx=code%this.remoteAdapters.size();
		return this.remoteAdapters.get(idx);
		
	}
	
	public void addClientAdapter(ClientAdapter clientAdapter){
		if(clientAdapter.isLocal()) {
			this.localAdapters.add(clientAdapter);
		}else{
			this.remoteAdapters.add(clientAdapter);
		}
		
	}

	
}
