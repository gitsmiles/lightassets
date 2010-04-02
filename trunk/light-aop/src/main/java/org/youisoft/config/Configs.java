package org.youisoft.config;

/**
 * 
 * @author janly
 *
 */
public class Configs{
	private java.util.List list=new java.util.ArrayList();
	
	/**
	 * 
	 * @param config
	 */
	public void setConfig(Config config){
		list.add(config);
	}

	/**
	 * @return the configs
	 */
	public Config[] getConfigs() {
		return (Config[])list.toArray(new Config[]{});
	}
	
	
}
