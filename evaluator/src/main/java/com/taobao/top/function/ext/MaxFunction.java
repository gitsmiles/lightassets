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
public class MaxFunction extends AbstractFunction {
	private static final java.util.List<Class<?>> supports;
	static{
		supports=new java.util.ArrayList<Class<?>>();
		supports.add(DoubleListOperator.class);
		supports.add(DoubleOperator.class);
	}

	@Override
	public String getName() {
		return "max";
	}

	@Override
	protected List<Class<?>> getSupportClassList() {
		return supports;
	}

	@Override
	public Operator<?> caculate(CalContext<?> context,Operator<?>... operator) {
		if(operator[0] instanceof DoubleOperator) return operator[0];
		DoubleListOperator dlo=((DoubleListOperator)operator[0]);
		List<Double> ld=dlo.value();
		Double db=ld.get(0);
		for(int i=1;i<ld.size();i++){
			if(ld.get(i).doubleValue()>db.doubleValue()){
				db=ld.get(i);
			}
		}

		return new DoubleOperator(db);
	}
	
}
