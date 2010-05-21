package com.fost.prop.web.wapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fost.prop.web.uum.UpdateDataUUM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/applicationContext-update1.xml","classpath:spring/application-proxy.xml",
		"classpath:spring/applicationContext-dao.xml"})
public class UpdateDataUUMTest {
	@Autowired
	private UpdateDataUUM updateDataUUM;

	@Test
	public void test1(){
		int i=0;
		updateDataUUM.list();
	}
}
