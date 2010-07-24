package com.fost.ssacache.adapter;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.ClientAdapter;

/**
 * @author Janly
 *
 */
public abstract class AbstractClientAdapter implements ClientAdapter {
	
	private String group;
	
	private boolean local;
	
	private int weight;
	

	@Override
	public int getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(int weight) {
		this.weight=weight;
	}

	@Override
	public String getGroup() {
		return this.group;
	}

	@Override
	public boolean isLocal() {
		
		return this.local;
	}


	@Override
	public void setGroup(String group) {
		
		this.group=group;
	}

	@Override
	public void setLocal(boolean local) {
		this.local=local;

	}

	@Override
	public boolean add(String key, int exp, Object value, int timeout)
			throws TimeoutException, InterruptedException {
		
		return false;
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		

	}

	@Override
	public boolean delete(String key, int timeout) throws TimeoutException,
			InterruptedException {
		
		return false;
	}

	@Override
	public void deleteWithNoReply(String key) throws InterruptedException {
		

	}

	@Override
	public Object get(String key, int timeout) throws TimeoutException,
			InterruptedException {
		
		return null;
	}

	@Override
	public boolean set(String key, int exp, Object value, int timeout)
			throws TimeoutException, InterruptedException {
		
		return false;
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		

	}

}
