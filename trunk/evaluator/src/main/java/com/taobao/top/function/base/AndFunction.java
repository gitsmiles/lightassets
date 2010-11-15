/**
 * 
 */
package com.taobao.top.function.base;

import com.taobao.top.CalContext;
import com.taobao.top.OpEnum;
import com.taobao.top.operator.Operator;
import com.taobao.top.operator.base.DoubleOperator;


/**
 * @author zijiang.jl
 *
 */
public class AndFunction extends AbstractLogicFunction{

	@Override
	public String getName() {
		return OpEnum.AND.name();
	}


	@Override
	protected Operator<?> caculate(CalContext<?> context,DoubleOperator... operator) {
		return operator[0].and(operator[1]);
	}

}
