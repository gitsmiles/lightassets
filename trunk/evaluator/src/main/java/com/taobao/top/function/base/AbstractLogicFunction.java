/**
 * 
 */
package com.taobao.top.function.base;

import com.taobao.top.CalContext;
import com.taobao.top.function.AbstractFunction;
import com.taobao.top.operator.Operator;
import com.taobao.top.operator.Operator.OperatorCallback;
import com.taobao.top.operator.base.DoubleOperator;
import com.taobao.top.operator.base.StringOperator;

/**
 * @author zijiang.jl
 *
 */
public abstract class AbstractLogicFunction extends AbstractFunction{

	
	private static final java.util.List<Class<?>> supports;
	static{
		supports=new java.util.ArrayList<Class<?>>();
		supports.add(StringOperator.class);
		supports.add(DoubleOperator.class);
	}
	
	protected java.util.List<Class<?>> getSupportClassList(){
		return supports;
	}
	
	@Override
	public Operator<?> caculate(CalContext<?> context,Operator<?>... operator) {
		DoubleOperator o1;
		DoubleOperator o2;
		
		if(operator[0] instanceof StringOperator){
			o1=(DoubleOperator)operator[0].convert(new OperatorCallback(){
				@Override
				public Operator<?> convert(Operator<?> operator) {
					return new DoubleOperator(operator.value().toString());
				}
			
			});
		}else{
			o1=(DoubleOperator)operator[0];
		}
		
		if(operator[1] instanceof StringOperator){
			o2=(DoubleOperator)operator[1].convert(new OperatorCallback(){
				@Override
				public Operator<?> convert(Operator<?> operator) {
					return new DoubleOperator(operator.value().toString());
				}
			
			});
		}else{
			o2=(DoubleOperator)operator[1];
		}
		
		return caculate(context,o1,o2);
	}
	
	
	protected abstract Operator<?> caculate(CalContext<?> context,DoubleOperator... operator);
	
}
