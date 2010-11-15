/**
 * 
 */
package com.taobao.top.bracket;

import com.taobao.top.BracketVisitor;
import com.taobao.top.CalContext;
import com.taobao.top.EvaluateException;
import com.taobao.top.OpEnum;
import com.taobao.top.operator.AbstractOperator;
import com.taobao.top.operator.Operator;
import com.taobao.top.function.FunctionRegistry;
import com.taobao.top.lfc.Initilize;

/**
 * 
 * @author zijiang.jl
 *
 * @param <R>
 * @param <P>
 */
public interface Bracket<R,P> extends Initilize{
	
	/**
	 * 
	 * @param fName
	 */
	public void setfName(String fName);
	
	/**
	 * 
	 * @return
	 */
	public String getfName();
	
	/**
	 * 
	 * @return
	 */
	public P getProperty();

	/**
	 * 
	 * @param property
	 */
	public void setProperty(P property);

	/**
	 * 
	 * @return
	 */
	public R getResource();

	/**
	 * 
	 * @param resource
	 */
	public void setResource(R resource);
	
	/**
	 * 
	 * @return
	 */
	public int getFunIndex();

	/**
	 * 
	 * @param funIndex
	 */
	public void setFunIndex(int funIndex);
	
	/**
	 * 
	 * @param idx
	 */
	public void setLeftIndex(int idx);
	
	/**
	 * 
	 * @return
	 */
	public int getLeftIndex();
	
	/**
	 * 
	 * @return
	 */
	public int getRightIndex();
	
	/**
	 * 
	 * @param idx
	 */
	public void setRightIndex(int idx);
	
	/**
	 * 
	 * @param obj
	 */
	public void setSource(Object obj);
	
	/**
	 * 
	 * @return
	 */
	public Object getSource();
	
	/**
	 * 
	 * @param content
	 */
	public void setContent(String content);
	
	/**
	 * 
	 * @return
	 */
	public String getContent();
	
	/**
	 * 
	 * @param bracket
	 */
	public void addChildBracket(Bracket<R,P> bracket);
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Bracket<R,P> getChildtBracket(int index);
	
	/**
	 * 
	 * @return
	 */
	public int childSize();
	
	/**
	 * 
	 * @return
	 */
	public Bracket<R, P> getParentBracket();

	/**
	 * 
	 * @param parentBracket
	 */
	public void setParentBracket(Bracket<R, P> parentBracket);
	
	/**
	 * 
	 * @param vistor
	 * @throws EvaluateException
	 */
	public void accept(CalContext<?> context,BracketVisitor<R,P> vistor) throws EvaluateException;
	
	/**
	 * 
	 * @return
	 * @throws EvaluateException
	 */
	public Operator<?> calculate(CalContext<?> context) throws EvaluateException;
	
	/**
	 * 
	 * @return
	 */
	public FunctionRegistry getFunctionRegistry();

	/**
	 * 
	 * @param functionRegistry
	 */
	public void setFunctionRegistry(FunctionRegistry functionRegistry);
	
	/**
	 * 
	 * @author zijiang.jl
	 *
	 */
	class OperateOperator extends AbstractOperator<OpEnum> {

	}
	
	
}
