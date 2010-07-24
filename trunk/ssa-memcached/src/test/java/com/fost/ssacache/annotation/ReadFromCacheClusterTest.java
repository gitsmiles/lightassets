package com.fost.ssacache.annotation;

import com.fost.ssacache.sample.TestService;


/**
 * 
 * @author Janly
 *
 */
@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@org.springframework.test.context.ContextConfiguration(locations={"classpath:service/service-fost-cache.xml","classpath:test-ssa-cluster-memcached.xml"})
public class ReadFromCacheClusterTest {

	@org.springframework.beans.factory.annotation.Autowired
	private TestService testService;
	
	
	
	@org.junit.Test
	public void testReadFromCache()	{
		
		String a=testService.hello("www", "bbb");
		org.junit.Assert.assertEquals("wwwbbb", a);
		
		
	}
}
