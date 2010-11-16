/**
 * 
 */
package com.taobao.top.impl;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import com.taobao.top.AbstractExpressionEvaluator;
import com.taobao.top.CalContext;
import com.taobao.top.EvaluateException;
import com.taobao.top.function.Function;


/**
 * 
 * @author zijiang.jl
 * 
 *
 */
public class MapResourceEvaluator extends AbstractExpressionEvaluator<Map<String,Object>,Properties>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MapResourceEvaluator(String str){
		super(str);
	}
	
	private static final java.util.Map<String,EvaluatorReference> evaluatorRef=new java.util.concurrent.ConcurrentHashMap<String,EvaluatorReference>();
	
	
	/**
	 * @author zijiang.jl
	 *
	 */
	public static class Utility{
		
		public static Object evaluate(CalContext<?> context,String conditions,Map<String,Object> resource,Properties property,List<Function> funs){
			if(conditions==null||"".equals(conditions)) return true;
			try {
				return getEvaluator(context,conditions,resource,property,funs).evaluate(context,resource, property);
			} catch (EvaluateException e) {
				throw new java.lang.RuntimeException(e.getMessage());
			}
		}
		
		private static MapResourceEvaluator getEvaluator(CalContext<?> context,String conditions,Map<String,Object> resource,Properties property,List<Function> funs){
			MapResourceEvaluator evaluator=null;
			EvaluatorReference ref=evaluatorRef.get(conditions);
			if(ref==null){
				evaluator=new MapResourceEvaluator(conditions);
				evaluator.setBracketClassName(MapResouceBracket.class.getName());
				if(funs!=null){
					for(Function fun:funs){
						evaluator.registerFunction(fun);
					}
				}
				evaluator.init();
				evaluatorRef.put(conditions,new EvaluatorReference(conditions,evaluator));
			}else{
				evaluator=ref.get();
				if(evaluator==null){
					evaluator=new MapResourceEvaluator(conditions);
					evaluator.setBracketClassName(MapResouceBracket.class.getName());
					evaluator.init();
				}
				
			}
			
			return evaluator;
		}
		
		public static Object evaluate(CalContext<?> context,String conditions,Map<String,Object> resource,Properties property){
			if(conditions==null||"".equals(conditions)) return true;
			try {
				return getEvaluator(context,conditions,resource,property,null).evaluate(context,resource, property);
			} catch (EvaluateException e) {
				throw new java.lang.RuntimeException(e.getMessage());
			}
		}
	}
	
	
	/**
	 * @author zijiang.jl
	 *
	 */
	private static class EvaluatorReference extends java.lang.ref.SoftReference<MapResourceEvaluator>{

		private String conditions;
		private static java.lang.ref.ReferenceQueue<MapResourceEvaluator> rq=new java.lang.ref.ReferenceQueue<MapResourceEvaluator>();
		static{
			java.util.concurrent.ScheduledExecutorService exe=java.util.concurrent.Executors.newScheduledThreadPool(1);
			exe.scheduleAtFixedRate(new java.lang.Runnable(){
				@Override
				public void run() {
					EvaluatorReference tr=null;
					while((tr=(EvaluatorReference)rq.poll())!=null){
						evaluatorRef.remove(tr.conditions);
					}
				}
				
			},5,5,TimeUnit.MINUTES);
		}

		public EvaluatorReference(String conditions,MapResourceEvaluator evaluator){
			super(evaluator,rq);
			this.conditions=conditions;
		}
	
	}

	
	public static int getBracketCacheSize(){
		return evaluatorRef.size();
	}
}


