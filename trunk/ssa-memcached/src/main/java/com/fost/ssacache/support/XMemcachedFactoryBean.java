package com.fost.ssacache.support;

import net.rubyeye.xmemcached.XMemcachedClientBuilder;

import org.springframework.beans.factory.FactoryBean;

public class XMemcachedFactoryBean implements FactoryBean {
	private java.util.List<java.net.InetSocketAddress> inetSocketAddresses;

	@Override
	public Object getObject() throws Exception {
		XMemcachedClientBuilder builder=new XMemcachedClientBuilder(inetSocketAddresses);
		return builder.build();
	}

	@Override
	public Class getObjectType() {
		return net.rubyeye.xmemcached.MemcachedClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public final void setInetSocketAddresses(java.util.List<String> inetSocketAddresses) {
		this.inetSocketAddresses=new java.util.ArrayList<java.net.InetSocketAddress>(inetSocketAddresses.size());
		
		java.net.InetSocketAddress address=null;
		String[] temp;
		for(String str:inetSocketAddresses){
			temp=str.split(":");
			if(temp.length>=2)
			this.inetSocketAddresses.add(new java.net.InetSocketAddress(temp[0], new Integer(temp[1]).intValue()));
		}

	}

	
}
