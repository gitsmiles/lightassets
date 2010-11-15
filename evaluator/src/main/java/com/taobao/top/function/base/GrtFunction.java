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
public class GrtFunction extends AbstractFunction{

	
	private static final java.util.List<Class<?>> supports;
	static{
		supports=new java.util.ArrayList<Class<?>>();
		supports.add(DoubleOperator.class);
	}
	@Override
	public String getName() {
		return OpEnum.GRT.name();
	}


	protected java.util.List<Class<?>> getSupportClassList(){
		return supports;
	}

	@Override
	public Operator<?> caculate(CalContext<?> context,Operator<?>... operator) {
		DoubleOperator o1=(DoubleOperator)operator[0];
		DoubleOperator o2=(DoubleOperator)operator[1];
		return o1.grt(o2);
	}
}