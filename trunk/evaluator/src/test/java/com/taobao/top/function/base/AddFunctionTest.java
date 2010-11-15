package com.taobao.top.function.base;


import org.junit.Assert;
import org.junit.Test;

import com.taobao.top.CalContext;
import com.taobao.top.function.FunctionCallback;
import com.taobao.top.function.base.AddFunction;
import com.taobao.top.operator.Operator;
import com.taobao.top.operator.base.DoubleOperator;
import com.taobao.top.operator.base.StringOperator;

public class AddFunctionTest {

	@Test
	public void testSupportOperator() {
		AddFunction af=new AddFunction();
		Assert.assertTrue(af.supportOperator(null,new StringOperator(),new StringOperator()));
	}

	@Test
	public void testCaculateIArray() {
		AddFunction af=new AddFunction();
		Operator<?> c=af.caculate(null,new DoubleOperator().setValue(1.0),new DoubleOperator().setValue(2.0));
		Assert.assertEquals(3.0,c.value());
		
		

		c=af.caculate(null,new StringOperator().setValue("ab"),new StringOperator().setValue("cd"));
		Assert.assertEquals("abcd",c.value());
	}

	@Test
	public void testCaculateFunctionCallback() {
		AddFunction af=new AddFunction();
		Operator<?> c=af.caculate(null,new FunctionCallback(){

			@Override
			public Operator<?> caculate(CalContext<?> context,Operator<?>... operator) {
				
				double a=Double.parseDouble(operator[0].value().toString());
				double b=Double.parseDouble(operator[1].value().toString());
				return new DoubleOperator().setValue(a+b);
				
			}
			
		},new DoubleOperator().setValue(1.0),new DoubleOperator().setValue(2.0));
		Assert.assertEquals(3.0,c.value());
	}

}
