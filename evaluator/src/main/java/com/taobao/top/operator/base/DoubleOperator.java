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
public class DoubleOperator extends AbstractOperator<Double> {
	
	public DoubleOperator(){
		
	}
	
	public DoubleOperator(double value){
		this.setValue(value);
	}
	
	public DoubleOperator(String value){
		this.setValue(Double.valueOf(value));
	}
	
	@Override
	public Operator<Double> or(Operator<Double> t) {
		this.setValue(Double.valueOf((this.value().byteValue()|t.value().byteValue())));
		return super.or(t);
	}

	@Override
	public Operator<Double> xor(Operator<Double> t) {
		this.setValue(Double.valueOf((this.value().byteValue()^t.value().byteValue())));
		return super.xor(t);
	}

	@Override
	public Operator<Double> and(Operator<Double> t) {
		this.setValue(Double.valueOf((this.value().byteValue()&t.value().byteValue())));
		return super.and(t);
	}

	@Override
	public Operator<Double> no() {
		this.setValue(Double.valueOf((~this.value().byteValue())));
		return super.no();
	}


	@Override
	public Operator<Double> eq(Operator<Double> t) {
		if(this.value().doubleValue()==t.value().doubleValue()) {
			this.setValue(1.0);
		}else{
			this.setValue(0.0);
		}
		return super.eq(t);
	}

	@Override
	public Operator<Double> neq(Operator<Double> t) {
		if(this.value().doubleValue()==t.value().doubleValue()) {
			this.setValue(0.0);
		}else{
			this.setValue(1.0);
		}
		return super.neq(t);
	}

	@Override
	public Operator<Double> grt(Operator<Double> t) {
		if(this.value().doubleValue()>t.value().doubleValue()) {
			this.setValue(1.0);
		}else{
			this.setValue(0.0);
		}
		return super.grt(t);
	}

	@Override
	public Operator<Double> les(Operator<Double> t) {
		if(this.value().doubleValue()<t.value().doubleValue()) {
			this.setValue(1.0);
		}else{
			this.setValue(0.0);
		}
		return super.les(t);
	}

	@Override
	public Operator<Double> gre(Operator<Double> t) {
		if(this.value().doubleValue()>=t.value().doubleValue()) {
			this.setValue(1.0);
		}else{
			this.setValue(0.0);
		}
		return super.gre(t);
	}

	@Override
	public Operator<Double> lee(Operator<Double> t) {
		if(this.value().doubleValue()<=t.value().doubleValue()) {
			this.setValue(1.0);
		}else{
			this.setValue(0.0);
		}
		return super.lee(t);
	}


	@Override
	public Operator<Double> add(Operator<Double> t) {
		this.setValue(this.value().doubleValue()+t.value().doubleValue());
		return super.add(t);
	}

	@Override
	public Operator<Double> sub(Operator<Double> t) {
		this.setValue(this.value().doubleValue()-t.value().doubleValue());
		return super.sub(t);
	}

	@Override
	public Operator<Double> mul(Operator<Double> t) {
		this.setValue(this.value().doubleValue()*t.value().doubleValue());
		return super.mul(t);
	}

	@Override
	public Operator<Double> div(Operator<Double> t) {
		this.setValue(this.value().doubleValue()/t.value().doubleValue());
		return super.div(t);
	}

	@Override
	public Operator<Double> mod(Operator<Double> t) {
		this.setValue(this.value().doubleValue()%t.value().doubleValue());
		return super.mod(t);
	}

	@Override
	public Operator<Double> plus() {
		return super.plus();
	}

	@Override
	public Operator<Double> minus() {
		this.setValue(-this.value().doubleValue());
		return super.minus();
	}

	
	
}
