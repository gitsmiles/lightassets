package com.fost.ssacache.cluster;

import com.fost.ssacache.ClientAdapter;


/**
 * @author Janly
 *
 */
public final class EventManager {
	
	private java.util.concurrent.BlockingQueue<CacheEvent> events;
	private java.util.Set<EventListener> listeners;
	private java.util.List<ClientAdapter> adapters;
	private int threadPoolSize=30;
	private static EventManager instance;
	
	private EventManager(){
		events=new java.util.concurrent.LinkedBlockingQueue<CacheEvent>(6);
		listeners=java.util.Collections.synchronizedSet(new java.util.HashSet<EventListener>(6));
		
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
	
	
	public final void synPublishEvent(CacheEvent cacheEvent){
		cacheEvent.setEventManager(this);
		for(EventListener listener:this.listeners){
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
	
	
	public final void addListener(EventListener listener){
		this.listeners.add(listener);
	}
	
	private final java.util.Set<EventListener> getListeners(){
		return this.listeners;
	}


	public final int getThreadPoolSize() {
		return threadPoolSize;
	}


	public final void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}
	
	
	
	public final java.util.List<ClientAdapter> getAdapters() {
		return adapters;
	}


	public final void setAdapters(java.util.List<ClientAdapter> adapters) {
		this.adapters = adapters;
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
			for(EventListener listener:EventManager.getInstance().getListeners()){
				if(listener.captureEvent(event)) listener.executeEvent(event,false);
			}
			
		}
		
		
	}

}