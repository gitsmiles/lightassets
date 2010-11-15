/**
 * 
 */
package com.taobao.top.operator.base;

import com.taobao.top.operator.AbstractOperator;
import com.taobao.top.operator.Operator;

/**
 * @author zijiang.jl
 *
 */
public class StringOperator extends AbstractOperator<String> {
	
	public StringOperator(){
		
	}
	
	public StringOperator(String value){
		this.setValue(value);
	}
	
	@Override
	public Operator<String> or(Operator<String> t) {
		if(this.value().equals("1")
				||this.value().equalsIgnoreCase("true")
				||t.value().equals("1")
				||t.value().equalsIgnoreCase("true")){
			return this.setValue("1");
		}
		this.setValue("0");
		return super.or(t);
	}

	@Override
	public Operator<String> xor(Operator<String> t) {
		if((this.value().equals("1")
				||this.value().equalsIgnoreCase("true"))
				&&(t.value().equals("0")
				||t.value().equalsIgnoreCase("false"))){
			return this.setValue("1");
		}
		
		if((this.value().equals("0")
				||this.value().equalsIgnoreCase("false"))
				&&(t.value().equals("1")
				||t.value().equalsIgnoreCase("true"))){
			return this.setValue("1");
		}
		this.setValue("0");
		return super.xor(t);
	}
	
	

	@Override
	public Operator<String> sam(Operator<String> t) {
		if((this.value().equals("1")
				||this.value().equalsIgnoreCase("true"))
				&&(t.value().equals("1")
				||t.value().equalsIgnoreCase("true"))){
			return this.setValue("1");
		}
		this.setValue("0");
		return super.sam(t);
	}

	@Override
	public Operator<String> and(Operator<String> t) {
		if((this.value().equals("1")
				||this.value().equalsIgnoreCase("true"))
				&&(t.value().equals("1")
				||t.value().equalsIgnoreCase("true"))){
			return this.setValue("1");
		}
		this.setValue("0");
		return super.and(t);
	}

	@Override
	public Operator<String> no() {
		if(this.value().equals("1")
				||this.value().equalsIgnoreCase("true")){
			return this.setValue("0");
		}
		if(this.value().equals("0")
				||this.value().equalsIgnoreCase("false")){
			return this.setValue("1");
		}
		return super.no();
	}

	@Override
	public Operator<String> add(Operator<String> t) {
		this.setValue(this.value()+t.value());
		return super.and(t);
	}

	@Override
	public Operator<String> eq(Operator<String> t) {
		this.setValue(this.value().equals(t.value())==true?"1":"0");
		return super.eq(t);
	}

	@Override
	public Operator<String> neq(Operator<String> t) {
		this.setValue(!this.value().equals(t.value())==true?"1":"0");
		return super.neq(t);
	}
	
}
