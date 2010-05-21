package com.fost.prop.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fost.prop.api.PropertiesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao.xml",
		"classpath:spring/applicationContext-service.xml", "classpath:spring/applicationContext-api.xml" })
public class AdminServiceTest {
	@Autowired
	private PropertiesService propertiesService;

	@Test
	public void test(){
		String[] applicationNames = { "装备", "gameId", "元宝", "global", "uum", "user" };
		int[] versions = { -1, -1, -1, -1, -1, -1 };		
		long begin = System.currentTimeMillis();
		propertiesService.getTree(applicationNames, versions);
		long end = System.currentTimeMillis();
		System.out.println("time is : "+(end-begin));
	}
}
