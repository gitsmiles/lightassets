package com.fost.ssacache;
import java.util.concurrent.TimeoutException;



/**
 * 
 * @author Janly
 *
 */
public interface Cache {


	public abstract Object get(final String key, final long timeout) throws TimeoutException, InterruptedException;

	
	//public abstract Object gets(final String key, final long timeout) throws TimeoutException, InterruptedException;
	
	
	public abstract boolean set(final String key, final int exp,final Object value, final long timeout) throws TimeoutException,InterruptedException;


	public abstract void setWithNoReply(final String key, final int exp,final Object value) throws InterruptedException;


	public abstract boolean add(final String key, final int exp,final Object value, final long timeout) throws TimeoutException,InterruptedException;


	public abstract void addWithNoReply(final String key, final int exp,final Object value) throws InterruptedException;
	
	
//	public abstract boolean replace();
//	
//	
//	public abstract void replaceWithNoReply();
//	
//	
//	public abstract boolean prepend();
//	
//	
//	public abstract boolean prependWithNoReply();
//	
//	
//	public abstract boolean append();
//	
//	
//	public abstract boolean appendWithNoReply();
	

	public abstract boolean delete(final String key, final int time) throws TimeoutException, InterruptedException;


	public void deleteWithNoReply(final String key) throws InterruptedException;
	
	
//	public void cas();
//	
//	public void incr();
//	
//	public void decr();
//	
//	public void flush();

	
	////Statistics
	
//	public void stats();
//	public void statsItems();
//	public void statsSlabs();
//	public void statsSizes();

}
