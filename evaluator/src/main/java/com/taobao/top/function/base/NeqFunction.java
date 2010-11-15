/**
 * 
 */
package com.taobao.top.function.base;

import com.taobao.top.CalContext;
import com.taobao.top.OpEnum;
import com.taobao.top.operator.Operator;
import com.taobao.top.operator.base.DoubleOperator;
import com.taobao.top.operator.base.StringOperator;

/**
 * @author zijiang.jl
 *
 */
public class NeqFunction extends AbstractArithmeticFunction {

	@Override
	public String getName() {
		return OpEnum.NEQ.name();
	}

	@Override
	protected Operator<?> caculate(CalContext<?> context,StringOperator... operator) {
		return operator[0].neq(operator[1]);
	}


	@Override
	protected Operator<?> caculate(CalContext<?> context,DoubleOperator... operator) {
		return operator[0].neq(operator[1]);
	}


}
