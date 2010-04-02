package com.fost.ssacache.sample;

/**
 * 
 * @author Janly
 *
 */
public interface TestService {

	
	@com.fost.ssacache.annotation.ReadFromCache(namespace="hello")
	public String hello(String a,String b);
	
}
