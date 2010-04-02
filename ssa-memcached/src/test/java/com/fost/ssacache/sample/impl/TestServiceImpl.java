package com.fost.ssacache.sample.impl;

import com.fost.ssacache.sample.TestService;

/**
 * 
 * @author Janly
 *
 */
public class TestServiceImpl implements TestService {

	@Override
	@com.fost.ssacache.annotation.ReadFromCache(namespace="hello")
	public String hello(String a,String b) {
		return a+b;
	}

	
}
