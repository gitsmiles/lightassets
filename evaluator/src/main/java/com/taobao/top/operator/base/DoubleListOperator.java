/**
 * 
 */
package com.taobao.top.operator.base;
import com.taobao.top.operator.AbstractOperator;

/**
 * @author zijiang.jl
 *
 */
public class DoubleListOperator extends AbstractOperator<java.util.List<Double>> {


	public DoubleListOperator(){
		
	}
	
	public DoubleListOperator(java.util.List<Double> list){
		this.setValue(list);
	}
}
