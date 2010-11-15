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
public interface FunctionCallback {

	public Operator<?> caculate(CalContext<?> context,Operator<?> ... operator);
}
