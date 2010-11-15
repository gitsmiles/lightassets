/**
 * 
 */
package com.taobao.top.operator;

/**
 * @author zijiang.jl
 * Define the base operation
 *
 */
public interface Operator<T> {
	public static final NullOperator nullInstance=new NullOperator();
	public static interface OperatorCallback{
		public Operator<?> convert(Operator<?> operator);
	}
	
	public T value();

	public Operator<T> setValue(T value);
	
	public Operator<?> convert(OperatorCallback callback);
	
	//==================base ====================
	public Operator<T> or(Operator<T> t);
	public Operator<T> orr(Operator<T> t);
	public Operator<T> sam(Operator<T> t);
	public Operator<T> xor(Operator<T> t);
	public Operator<T> and(Operator<T> t);
	public Operator<T> andd(Operator<T> t);
	public Operator<T> no();
	public Operator<T> eqq(Operator<T> t);
	public Operator<T> eq(Operator<T> t);
	public Operator<T> neq(Operator<T> t);
	public Operator<T> grt(Operator<T> t);
	public Operator<T> les(Operator<T> t);
	public Operator<T> gre(Operator<T> t);
	public Operator<T> lee(Operator<T> t);
	public Operator<T> grl(Operator<T> t);
	public Operator<T> lel(Operator<T> t);
	public Operator<T> add(Operator<T> t);
	public Operator<T> sub(Operator<T> t);
	public Operator<T> mul(Operator<T> t);
	public Operator<T> div(Operator<T> t);
	public Operator<T> mod(Operator<T> t);
	public Operator<T> plus();
	public Operator<T> minus();

	public static class NullOperator extends AbstractOperator<Object> {
		private NullOperator(){
			
		}

	}
	
}
