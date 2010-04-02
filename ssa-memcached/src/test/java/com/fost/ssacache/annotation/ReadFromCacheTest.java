package com.fost.ssacache.annotation;

import com.fost.ssacache.sample.TestService;



@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@org.springframework.test.context.ContextConfiguration(locations={"classpath:service/service-fost-cache.xml","classpath:service/service-ssa-memcached.xml","classpath:test-ssa-memcached.xml"})
public class ReadFromCacheTest {

	@org.springframework.beans.factory.annotation.Autowired
	private TestService testService;
	
	
	
	@org.junit.Test
	public void testReadFromCache()	{
		
		String a=testService.hello("www", "bbb");
		org.junit.Assert.assertEquals("wwwbbb", a);
		
		
	}
}
