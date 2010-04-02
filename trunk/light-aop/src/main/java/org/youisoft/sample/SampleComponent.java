package org.youisoft.sample;
/**
 * 
 * @author janly
 *
 */
public class SampleComponent {
	
	public Response invoke(org.youisoft.sample.Request req){
		System.out.println("=====Class loader modify bytecode success.========");
		System.out.println("=====================invoke=SUCCESS======================");
		return new Response();
	}
	
	public Response invoke(org.youisoft.sample.Request req,org.youisoft.sample.Response res){
		System.out.println("=====Class loader modify bytecode success.========");
		System.out.println("===================invokeAdd===SUCCESS======================");
		return new Response();
	}
	
	public Response invoke(org.youisoft.sample.Request req,java.lang.Throwable t){
		System.out.println("=====Class loader modify bytecode success.========");
		System.out.println("===================invokeAdd===SUCCESS======================");
		return new Response();
	}
	
	
	public static Response invokeStatic(org.youisoft.sample.Request req){
		System.out.println("=====Class loader modify bytecode success.========");
		System.out.println("===================invokeStatic===SUCCESS======================");
		return new Response();
	}
	
	public static Response invokeStatic(org.youisoft.sample.Request req,org.youisoft.sample.Response res){
		System.out.println("=====Class loader modify bytecode success.========");
		System.out.println("==================invokeStaticAdd====SUCCESS======================");
		return new Response();
	}
	
	public static Response invokeStatic(org.youisoft.sample.Request req,java.lang.Throwable t){
		System.out.println("=====Class loader modify bytecode success.========");
		System.out.println("==================invokeStaticAdd====SUCCESS======================");
		return new Response();
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
