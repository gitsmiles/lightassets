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
public interface Function {
	
	public String getName();
	
	public boolean supportOperator(CalContext<?> context,Operator<?> ... operator);
	
	public Operator<?> caculate(CalContext<?> context,Operator<?> ... operator);
	
	public Operator<?> caculate(CalContext<?> context,FunctionCallback callback,Operator<?> ... operator);

}
