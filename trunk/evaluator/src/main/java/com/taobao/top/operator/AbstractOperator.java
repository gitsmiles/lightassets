/**
 * 
 */
package com.taobao.top.operator;

/**
 * @author zijiang.jl
 *
 */
public abstract class AbstractOperator<T> implements Operator<T> {
	private T value;
	
	public AbstractOperator(){
		
	}

	public AbstractOperator(T value){
		this.value=value;
	}
	
	public final T value() {
		return value;
	}

	public final Operator<T> setValue(T value) {
		this.value = value;
		return this;
	}

	@Override
	public Operator<T> or(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> orr(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> xor(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> sam(Operator<T> t) {

		return this;
	}

	@Override
	public Operator<T> and(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> andd(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> no() {
		
		return this;
	}

	@Override
	public Operator<T> eqq(Operator<T> t) {
		return this.eq(t);
	}

	@Override
	public Operator<T> eq(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> neq(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> grt(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> les(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> gre(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> lee(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> grl(Operator<T> t) {
		
		return this.grt(t);
	}

	@Override
	public Operator<T> lel(Operator<T> t) {
		
		return this.les(t);
	}

	@Override
	public Operator<T> add(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> sub(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> mul(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> div(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> mod(Operator<T> t) {
		
		return this;
	}

	@Override
	public Operator<T> plus() {
		
		return this;
	}

	@Override
	public Operator<T> minus() {
		
		return this;
	}

	@Override
	public Operator<?> convert(OperatorCallback callback) {
		return callback.convert(this);
	}

}
