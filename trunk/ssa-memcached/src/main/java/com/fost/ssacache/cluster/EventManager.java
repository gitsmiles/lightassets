package com.fost.ssacache.cluster;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.ClientAdapter;
import com.fost.ssacache.cluster.listener.AddEventListener;
import com.fost.ssacache.cluster.listener.DeleteEventListener;
import com.fost.ssacache.cluster.listener.RecoverEventListener;
import com.fost.ssacache.cluster.listener.SetEventListener;


/**
 * @author Janly
 *
 */
public final class EventManager {
	private final org.apache.commons.logging.Log logger=org.apache.commons.logging.LogFactory.getLog(EventManager.class);
	private static EventManager instance;
	
	private static java.util.Set<EventListener> listeners=java.util.Collections.synchronizedSet(new java.util.HashSet<EventListener>(6));
	{
		listeners.add(new AddEventListener());
		listeners.add(new DeleteEventListener());
		listeners.add(new RecoverEventListener());
		listeners.add(new SetEventListener());
	}
	
	private java.util.concurrent.BlockingQueue<CacheEvent> events;
	private java.util.Map<String, java.util.List<ClientAdapter>> groupAdapterMap;
	private int threadPoolSize=30;
	
	
	private EventManager(){
		this.events=new java.util.concurrent.LinkedBlockingQueue<CacheEvent>(6);
		this.groupAdapterMap=new java.util.concurrent.ConcurrentHashMap<String, java.util.List<ClientAdapter>>(1);
	}
	
	
	public static final EventManager getInstance(){
		if(instance==null){
			synchronized(EventManager.class){
				if(instance==null){
					instance=new EventManager();
					Thread thread=new EventThread(instance);
					thread.setDaemon(true);
					thread.start();
				}
			}
		}

		return instance;
	}
	
	
	public final void asynPublishEvent(CacheEvent cacheEvent){
		try{
			cacheEvent.setEventManager(this);
			events.put(cacheEvent);
		}catch(InterruptedException e){
			
		}
	}
	
	
	public final void synPublishEvent(CacheEvent cacheEvent) throws TimeoutException,InterruptedException{
		cacheEvent.setEventManager(this);
		for(EventListener listener:listeners){
			if(listener.captureEvent(cacheEvent)) listener.executeEvent(cacheEvent,true);
		}
	}
	
	
	private final CacheEvent takeEvent(){
		try {
			return this.events.take();
		} catch (InterruptedException e) {
			
			
		}
		return null;
	}
	
	
	public static final void addListener(EventListener listener){
		listeners.add(listener);
	}
	

	public final int getThreadPoolSize() {
		return threadPoolSize;
	}


	public final void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	
	public final java.util.Map<String, java.util.List<ClientAdapter>> getGroupAdapterMap() {
		return this.groupAdapterMap;
	}







	///////////////
	/**
	 * Janly
	 */
	static class EventThread extends java.lang.Thread{
		private EventManager eventManager;
		private java.util.concurrent.ExecutorService executorService;

		private EventThread(EventManager eventManager){
			this.eventManager=eventManager;
			executorService=java.util.concurrent.Executors.newFixedThreadPool(this.eventManager.getThreadPoolSize());
		}
		@Override
		public void run() {
			while(true){
				CacheEvent event=this.eventManager.takeEvent();
				if(event!=null){
					executorService.execute(this.eventManager.new EventJob(event));
				}
			}
			
		}
		
	}
	
	//////
	/**
	 * Janly
	 */
	class EventJob implements java.lang.Runnable{
		private CacheEvent event;
		private EventJob(CacheEvent event){
			this.event=event;
		}
		@Override
		public void run() {
			for(EventListener listener:listeners){
				if(listener.captureEvent(event)) {
					try {
						listener.executeEvent(event,false);
					} catch (Exception e) {
						logger.warn(listener.getClass().getCanonicalName()+" failure", e);
					}
				}
			}
			
		}
		
		
	}

}
