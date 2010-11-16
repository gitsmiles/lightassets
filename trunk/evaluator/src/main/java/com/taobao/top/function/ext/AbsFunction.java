/**
 * 
 */
package com.taobao.top.function.ext;

import java.util.List;

import com.taobao.top.CalContext;
import com.taobao.top.function.AbstractFunction;
import com.taobao.top.operator.Operator;
import com.taobao.top.operator.base.DoubleOperator;

/**
 * @author zijiang.jl
 *
 */
public class AbsFunction extends AbstractFunction{

	private static final java.util.List<Class<?>> supports;
	static{
		supports=new java.util.ArrayList<Class<?>>();
		supports.add(DoubleOperator.class);
	}

	@Override
	public String getName() {
		return "abs";
	}

	@Override
	protected List<Class<?>> getSupportClassList() {
		return supports;
	}

	@Override
	public Operator<?> caculate(CalContext<?> context,Operator<?>... operator) {
		DoubleOperator d=(DoubleOperator)operator[0];
		d.setValue(Double.valueOf(java.lang.StrictMath.abs(d.value().doubleValue())));
		return d;
	}
}
