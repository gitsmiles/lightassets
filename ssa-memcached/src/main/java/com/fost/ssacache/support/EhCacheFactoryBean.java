package com.fost.ssacache.support;
import java.net.URL;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.FactoryBean;

public class EhCacheFactoryBean implements FactoryBean {
	private String configurationFileName;
	private URL configurationURL;
	private String cacheName;

	@Override
	public Object getObject() throws Exception {
		CacheManager cacheManager=null;
		if(this.configurationURL!=null){
			cacheManager=new CacheManager(configurationFileName);
		}else if(this.configurationFileName!=null){
			cacheManager=new CacheManager(configurationURL);
		} else{
			cacheManager=new CacheManager();
		}
		
		return cacheManager.getCache(this.cacheName);
	}

	@Override
	public Class getObjectType() {
		return Ehcache.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}


	public final String getConfigurationFileName() {
		return configurationFileName;
	}

	public final void setConfigurationFileName(String configurationFileName) {
		this.configurationFileName = configurationFileName;
	}

	public final URL getConfigurationURL() {
		return configurationURL;
	}

	public final void setConfigurationURL(URL configurationURL) {
		this.configurationURL = configurationURL;
	}

	public final String getCacheName() {
		return cacheName;
	}

	public final void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	
	
	

}
