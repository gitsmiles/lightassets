/**
 * 
 */
package com.taobao.top.function;


/**
 * @author zijiang.jl
 *
 */
public interface FunctionRegistry {
	
	public void registrer(Function function);
	
	
	public void unRegistrer(String functionName);
	
	public Function findFunction(String functionName);
	
	
}
