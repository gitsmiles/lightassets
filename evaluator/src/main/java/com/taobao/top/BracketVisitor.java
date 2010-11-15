/**
 * 
 */
package com.taobao.top;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.top.bracket.Bracket;
import com.taobao.top.operator.Operator;

/**
 * 
 * @author zijiang.jl
 * 
 * 访问者类，提供对bracket的访问。
 *
 * @param <R>
 * @param <P>
 */
public final class BracketVisitor<R,P> {
	
	protected static final Log logger=LogFactory.getLog(BracketVisitor.class);
	private Operator<?> value;
	private R resource;
	private P property;
    
	/**
	 * 
	 * @param bracket
	 * @throws EvaluateException
	 */
	public void visit(CalContext<?> context,Bracket<R,P> bracket) throws EvaluateException{
		this.value=bracket.calculate(context);
	}


	/**
	 * 
	 * @return
	 */
	public Operator<?> getValue() {
		return this.value;
	}

	/**
	 * 
	 * @return
	 */
	public P getProperty() {
		return property;
	}

	/**
	 * 
	 * @param property
	 */
	public void setProperty(P property) {
		this.property = property;
	}


	/**
	 * 
	 * @param resource
	 */
	public void setResource(R resource) {
		this.resource=resource;
		
	}

	

	/**
	 * 
	 * @return
	 */
	public R getResource() {
		return this.resource;
	}
}
