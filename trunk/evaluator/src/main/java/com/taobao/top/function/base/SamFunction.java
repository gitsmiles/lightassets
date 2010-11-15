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
public class SamFunction extends AbstractLogicFunction{

	@Override
	public String getName() {
		return OpEnum.SAM.name();
	}

	@Override
	protected Operator<?> caculate(CalContext<?> context,DoubleOperator... operator) {
		return operator[0].sam(operator[1]);
	}
}
