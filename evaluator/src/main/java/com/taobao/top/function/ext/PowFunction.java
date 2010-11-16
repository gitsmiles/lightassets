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
public class PowFunction extends AbstractFunction{

	private static final java.util.List<Class<?>> supports;
	static{
		supports=new java.util.ArrayList<Class<?>>();
		supports.add(DoubleOperator.class);
	}

	@Override
	public String getName() {
		return "pow";
	}

	@Override
	protected List<Class<?>> getSupportClassList() {
		return supports;
	}

	@Override
	public Operator<?> caculate(CalContext<?> context,Operator<?>... operator) {
		DoubleOperator d1=(DoubleOperator)operator[0];
		DoubleOperator d2=(DoubleOperator)operator[1];
		d1.setValue(Double.valueOf(java.lang.StrictMath.pow(d1.value().doubleValue(),d2.value().doubleValue())));
		return d1;
	}

}
