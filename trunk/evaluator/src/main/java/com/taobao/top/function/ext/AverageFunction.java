/**
 * 
 */
package com.taobao.top.function.ext;

import java.util.List;

import com.taobao.top.CalContext;
import com.taobao.top.function.AbstractFunction;
import com.taobao.top.operator.Operator;
import com.taobao.top.operator.base.DoubleListOperator;
import com.taobao.top.operator.base.DoubleOperator;

/**
 * @author zijiang.jl
 *
 */
public class AverageFunction extends AbstractFunction{


	private static final java.util.List<Class<?>> supports;
	static{
		supports=new java.util.ArrayList<Class<?>>();
		supports.add(DoubleListOperator.class);
		supports.add(DoubleOperator.class);
	}

	@Override
	public String getName() {
		return "average";
	}

	@Override
	protected List<Class<?>> getSupportClassList() {
		return supports;
	}

	@Override
	public Operator<?> caculate(CalContext<?> context,Operator<?>... operator) {
		
		if(operator[0] instanceof DoubleOperator) return operator[0];
		
		DoubleListOperator dlo=((DoubleListOperator)operator[0]);
		java.util.List<Double> ld=dlo.value();
		double a=0;
		for(Double d:ld){
			a+=d.doubleValue();
		}
		return new DoubleOperator(a/ld.size());
	}
}
