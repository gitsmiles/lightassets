package com.taobao.top.impl;


import org.junit.Test;
import com.taobao.top.EvaluateException;

public class MapResourceEvaluatorTest {
	
	@Test
	public void testMapResourceEvaluator() {
		String exp="$logflag$!=session&f()+f($errorCode$=0,$timestamp4$!=-1)&($errorCode$=0&$timestamp4$!=-1&$timestamp4$<10000)";
		//-3+-5/-1-4*-5===>22
		exp="sum($a$)";
		String res="0,61.152.169.197,null,xml,12011172,taobao.taobaoke.items.get,0,0,172.23.21.129,null,2.0,md5,3,null,4535000,13,15,1287849657193,0,0,-1,2,0,0,0,13,0,0,3,3";
		
		String[] re=res.split(",");
		java.util.Map<String, Object> map=new java.util.HashMap<String, Object>();
		for(int i=0;i<re.length;i++){
			map.put(""+(i+1),re[i]);
		}

		map.put("0", "1");
 
		MapResourceEvaluator evaluator=new MapResourceEvaluator(exp);
		evaluator.setBracketClassName(MapResouceBracket.class.getName());
		evaluator.init();
		try {
			long st=System.currentTimeMillis();
			Object obj=evaluator.evaluate(null,map, p);
			
			System.out.println(System.currentTimeMillis()-st);
		} catch (EvaluateException e) {
			org.junit.Assert.fail();
			
		}
	}
	
	@org.junit.Test
	public void testParenal(){
		
		final int count=500;      //4971;
		final int arrayCnt=405;
		
		//========================================
		final String[] str=new String[arrayCnt];
		for(int i=0;i<arrayCnt;i++){
			str[i]="$logflag$!=session&($errorCode$=0&$format$=json&$tag$!="+i+")";
		}
		//-3+-5/-1-4*-5===>22
		//
		
		final String res="0, 222.76.213.45, null,json, 12029422, taobao.user.get, 0, 0, null, 172.23.21.129, \ufffd\ufffd\ufffd\ufffd, 2.0, md5, -1, null, 51000, 6, 7, 1285516815032, 0, 0, 0, 1, 0, 0, 0, 0, 6, 0, 0";
		final String[] resource=res.split(",");
		final java.util.Map<String, Object> map=new java.util.HashMap<String, Object>();
		for(int i=0;i<resource.length;i++){
			map.put(""+(i+1),resource[i]);
		}
		
		final java.util.concurrent.atomic.AtomicInteger ai=new java.util.concurrent.atomic.AtomicInteger(0);
		final java.util.concurrent.CountDownLatch cdl=new java.util.concurrent.CountDownLatch(count);
		
		
		final java.util.concurrent.CyclicBarrier cb=new java.util.concurrent.CyclicBarrier(count,
				
				new java.lang.Runnable(){
					@Override
					public void run() {
						System.out.println("count:"+count+" reache,and begin");
					}
		});
		
		
	

		for(int i=0;i<count;i++){
			
			new java.lang.Thread(new java.lang.Runnable(){

				@Override
				public void run() {
					try {
						cb.await();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					int cnt=ai.addAndGet(1);
					org.junit.Assert.assertEquals("1.0",MapResourceEvaluator.Utility.evaluate(null,str[cnt%arrayCnt],map, p).toString());
					cdl.countDown();
				}
			
				
			}).start();
		}
		
		
		try {
			cdl.await();
			System.out.println("thread "+ai.get());
			System.out.println("cache count="+MapResourceEvaluator.getBracketCacheSize());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	
	}
	
	
	
	@Test
	public void testUtility() {
		
		
		String str1="$logflag$!=session&($errorCode$=0&$format$=json&$tag$!=3)";
		String str2="$logflag$!=session&($errorCode$=0&$format$=json&$tag$!=3)";
		String str3="$logflag$!=session&($errorCode$=0&$format$=json&$tag$!=3)";
		
		
		String res1="0,124.205.228.41,null,xml,12086002,taobao.itemcats.get,0,0,null,172.23.21.129,null,2.0,md5,3,null,42000,1,2,1285516818630,0,0,0,-1,1,0,0,0,1,0,0";
		String[] resource1=res1.split(",");
		java.util.Map<String, Object> map1=new java.util.HashMap<String, Object>();
		for(int i=0;i<resource1.length;i++){
			map1.put(""+(i+1),resource1[i]);
		}
		
		String res2="0,60.173.26.84,null,xml,12005431,taobao.user.get,248,0,null,172.23.21.129,汤翔2008,2.0,md5,4,null,65000,6,15,1285516814975,8,0,0,1,0,0,0,0,6,0,0";
		String[] resource2=res2.split(",");
		java.util.Map<String, Object> map2=new java.util.HashMap<String, Object>();
		for(int i=0;i<resource2.length;i++){
			map2.put(""+(i+1),resource2[i]);
		}
		
		String res3="0, 222.76.213.45, null,json, 12029422, taobao.user.get, 0, 0, null, 172.23.21.129, \ufffd\ufffd\ufffd\ufffd, 2.0, md5, 4, null, 51000, 6, 7, 1285516815032, 0, 0, 0, 1, 0, 0, 0, 0, 6, 0, 0";
		String[] resource3=res3.split(",");
		java.util.Map<String, Object> map3=new java.util.HashMap<String, Object>();
		for(int i=0;i<resource3.length;i++){
			map3.put(""+(i+1),resource3[i]);
		}
		
		
		Object b1=MapResourceEvaluator.Utility.evaluate(null,str1, map1, p);
		Object b2=MapResourceEvaluator.Utility.evaluate(null,str2, map2, p);
		Object b3=MapResourceEvaluator.Utility.evaluate(null,str3, map3, p);
		
		
		org.junit.Assert.assertEquals("0.0", b1.toString());
		org.junit.Assert.assertEquals("0.0",b2.toString());
		org.junit.Assert.assertEquals("1.0", b3.toString());
		
		
		
	}
	
	
	
	private static java.util.Properties p=new java.util.Properties();
	@org.junit.BeforeClass
	public static void before(){
		p.put("a",0);
		p.put("logflag",1);
		p.put("remoteIp",2);
		p.put("partnerId",3);
		p.put("format",4);
		p.put("errorCode",8);
		p.put("version",12);
		p.put("signMethod",13);
		p.put("tag",14);
		p.put("id",15);
		p.put("responseMappingTime",16);
		p.put("serviceConsumeTime",17);
		p.put("transactionConsumeTime",18);
		p.put("timestamp0",19);
		p.put("timestamp1",20);
		p.put("timestamp2",21);
		p.put("timestamp3",22);
		p.put("timestamp4",23);
		p.put("timestamp5",24);
		p.put("timestamp6",25);
		p.put("timestamp7",26);
		p.put("timestamp8",27);
		p.put("timestamp9",28);
		p.put("timestamp10",29);
		p.put("timestamp11",30);
	}
}
