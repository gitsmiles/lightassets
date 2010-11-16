/**
 * 
 */
package com.taobao.top.function;

import com.taobao.top.function.base.*;
import com.taobao.top.function.clustering.KMeansFunction;
import com.taobao.top.function.ext.AbsFunction;
import com.taobao.top.function.ext.AverageFunction;
import com.taobao.top.function.ext.ExpFunction;
import com.taobao.top.function.ext.MaxFunction;
import com.taobao.top.function.ext.MinFunction;
import com.taobao.top.function.ext.PowFunction;
import com.taobao.top.function.ext.RandomFunction;
import com.taobao.top.function.ext.RoundFunction;
import com.taobao.top.function.ext.SumFunction;

/**
 * @author zijiang.jl
 *
 */
public abstract class AbstractFunctionRegistry implements FunctionRegistry{

	private static final java.util.Map<String, Function> baseMaps;
	private final java.util.Map<String, Function> customFuns;
	static {
		
		
		baseMaps=new java.util.HashMap<String, Function>();
		
		Class<?>[] clazz={
				AddFunction.class,
				AnddFunction.class,
				AndFunction.class,
				DivFunction.class,
				EqFunction.class,
				EqqFunction.class,
				FunFunction.class,
				GreFunction.class,
				GrlFunction.class,
				GrtFunction.class,
				LeeFunction.class,
				LelFunction.class,
				LesFunction.class,
				MinusFuntion.class,
				ModFunction.class,
				MulFunction.class,
				NeqFunction.class,
				NoFunction.class,
				OrFunction.class,
				OrrFunction.class,
				PlusFunction.class,
				SubFunction.class,
				XorFunction.class,
				XorrFunction.class,
				
				//====================
				AbsFunction.class,
				AverageFunction.class,
				ExpFunction.class,
				MaxFunction.class,
				MinFunction.class,
				PowFunction.class,
				RandomFunction.class,
				RoundFunction.class,
				SumFunction.class,
				
				//==================
				KMeansFunction.class
		};
		
		Function f=null;
		for(Class<?> cz:clazz){
			try {
				f=(Function)cz.newInstance();
				baseMaps.put(f.getName(), f);
			} catch (Exception e) {
			}
		}	
	}
	
	
	public AbstractFunctionRegistry(){
		customFuns=new java.util.concurrent.ConcurrentHashMap<String, Function>();
	}
	
	
	
	@java.lang.Override
	public void registrer(Function function){
		customFuns.put(function.getName(), function);
	}
	
	
	@java.lang.Override
	public void unRegistrer(String functionName){
		customFuns.remove(functionName);
	}
	
	
	@java.lang.Override
	public Function findFunction(String functionName){
		Function fun=baseMaps.get(functionName);
		if(fun!=null) return fun;
		fun=customFuns.get(functionName);
		return fun;
	}



}
