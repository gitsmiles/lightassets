package com.fost.ssacache.sample;

public interface TestService {

	
	@com.fost.ssacache.annotation.ReadFromCache(namespace="hello")
	public String hello(String a,String b);
	
}
