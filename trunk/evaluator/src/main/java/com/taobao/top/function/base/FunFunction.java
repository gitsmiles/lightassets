/**
 * 
 */
package com.taobao.top.function.base;
import com.taobao.top.CalContext;
import com.taobao.top.function.AbstractFunction;
import com.taobao.top.operator.Operator;

/**
 * @author zijiang.jl
 *
 */
public class FunFunction extends AbstractFunction{

	
	private static final java.util.List<Class<?>> supports;
	static{
		supports=new java.util.ArrayList<Class<?>>();
		supports.add(Operator.NullOperator.class);
	}
	@Override
	public String getName() {
		return "f";
	}


	protected java.util.List<Class<?>> getSupportClassList(){
		return supports;
	}

	@Override
	public Operator<?> caculate(CalContext<?> context,Operator<?>... operator) {
		return Operator.nullInstance;
	}

}
