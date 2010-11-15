/**
 * 
 */
package com.taobao.top;

import com.taobao.top.function.Function;
import com.taobao.top.lfc.LifeCircle;;


/**
 * 
 * @author zijiang.jl
 *
 * @param <R>
 * @param <P>
 */
public interface ExpressionEvaluator<R,P> extends LifeCircle{

	/**
	 * 
	 * @param resource
	 * @param property
	 * @return
	 * @throws EvaluateException
	 */
	public Object evaluate(CalContext<?> context,R resource,P property) throws EvaluateException;
	
	/**
	 * 
	 * @param bracketClassName
	 */
	public void setBracketClassName(String bracketClassName);
	
	/**
	 * 
	 * @param bracketVisitor
	 */
	public void setBracketVisitor(BracketVisitor<R,P> bracketVisitor);
	
	/**
	 * 
	 * @return
	 */
	public BracketVisitor<R,P> getBracketVisitor();
	
	/**
	 * 
	 * @param expressionTransformer
	 */
	public void setExpressionTransformer(ExpressionTransformer expressionTransformer);
	
	/**
	 * 
	 * @return
	 */
	public ExpressionTransformer getExpressionTransformer();
	
	
	/**
	 * 
	 * @param function
	 */
	public void registerFunction(Function function);
	
	/**
	 * 
	 * @param functionName
	 */
	public void unRegisterFunction(String functionName);
	
}
