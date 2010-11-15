/**
 * 
 */
package com.taobao.top.function;

import com.taobao.top.CalContext;
import com.taobao.top.operator.Operator;

/**
 * @author zijiang.jl
 *
 */
public abstract class AbstractFunction implements Function {
	
	
	@Override
	public boolean supportOperator(CalContext<?> context,Operator<?>... operator) {
		for(Operator<?> op:operator){
			if(!this.getSupportClassList().contains(op.getClass())) return false;
		}
		return true;
	}
	
	protected abstract java.util.List<Class<?>> getSupportClassList();
	
	@Override
	public Operator<?> caculate(CalContext<?> context,FunctionCallback callback,Operator<?> ... operator) {
		return callback.caculate(context,operator);
	}

}
