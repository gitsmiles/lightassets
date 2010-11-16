package com.taobao.top.function.ext;

import org.junit.Test;

import com.taobao.top.operator.base.DoubleOperator;

public class RoundFunctionTest {

	@Test
	public void testCaculateCalContextOfQOperatorOfQArray() {
		DoubleOperator d=new DoubleOperator();
		RoundFunction rf=new RoundFunction();
		d.setValue(3.0);
		org.junit.Assert.assertEquals(3.0, rf.caculate(null, d).value());
		d.setValue(3.4);
		org.junit.Assert.assertEquals(3.0, rf.caculate(null, d).value());
		d.setValue(3.487);
		org.junit.Assert.assertEquals(3.0, rf.caculate(null, d).value());
		d.setValue(3.5);
		org.junit.Assert.assertEquals(4.0, rf.caculate(null, d).value());
		d.setValue(3.6);
		org.junit.Assert.assertEquals(4.0, rf.caculate(null, d).value());
		
		
		
		d.setValue(-3.0);
		org.junit.Assert.assertEquals(-3.0, rf.caculate(null, d).value());
		
		d.setValue(-3.4);
		org.junit.Assert.assertEquals(-3.0, rf.caculate(null, d).value());
		d.setValue(-3.49);
		org.junit.Assert.assertEquals(-3.0, rf.caculate(null, d).value());
		d.setValue(-3.5);
		org.junit.Assert.assertEquals(-3.0, rf.caculate(null, d).value());
		d.setValue(-3.6);
		org.junit.Assert.assertEquals(-4.0, rf.caculate(null, d).value());
	}

}
