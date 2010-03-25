package org.youisoft.sample;
/**
 * 
 * @author janly
 *
 */
public class SampleService {
	public Response sample(Request req){
			Response r1=null;
			try{
				System.out.println("in catch");
			}catch(java.lang.RuntimeException t){
				
			}
		
			r1=new Response();
			System.out.println("in SampleService");
				
			return r1;
	}
	
	
	
	public Response enter(Request req){
		
		System.out.println("enter");
		return null;
		
	}
	
	
	public Response exit(Request req){
		
		System.out.println("exit");
		return null;
		
	}
	

}
