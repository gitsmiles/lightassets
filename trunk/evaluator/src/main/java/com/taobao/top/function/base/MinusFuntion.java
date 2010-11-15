/**
 * 
 */
package com.taobao.top.function.base;

import com.taobao.top.CalContext;
import com.taobao.top.OpEnum;
import com.taobao.top.function.AbstractFunction;
import com.taobao.top.operator.Operator;
import com.taobao.top.operator.base.DoubleOperator;

/**
 * @author zijiang.jl
 *
 */
public class MinusFuntion extends AbstractFunction{

	
	private static final java.util.List<Class<?>> supports;
	static{
		supports=new java.util.ArrayList<Class<?>>();
		supports.add(DoubleOperator.class);
	}
	@Override
	public String getName() {
		return OpEnum.MINUS.name();
	}


	protected java.util.List<Class<?>> getSupportClassList(){
		return supports;
	}

	@Override
	public Operator<?> caculate(CalContext<?> context,Operator<?>... operator) {
		return operator[0].minus();
	}
}