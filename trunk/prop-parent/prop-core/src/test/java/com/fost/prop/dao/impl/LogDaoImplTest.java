package com.fost.prop.dao.impl;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fost.prop.dao.LogDao;
import com.fost.prop.model.OperationLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao.xml" })
public class LogDaoImplTest {
	private final static Logger LOGGER = LoggerFactory.getLogger(LogDaoImplTest.class);
	@Autowired
	private LogDao logDao;

	@Test
	public void insert(){
		OperationLog log = new OperationLog();
		log.setEvent("aa");
		try{
			long x = logDao.insertLog(log);
		    LOGGER.info(String.valueOf(x));
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void find(){
		OperationLog log = new OperationLog();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		date = cal.getTime();
		String time = df.format(date);

		log.setBeginTime(time);
		log.setEndTime(time);
        log.setEvent("add");
        log.setOperatorName("rzp");
		
		System.out.println(logDao.findLog(log).size());
	}
}
