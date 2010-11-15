/**
 * 
 */
package com.taobao.top;

/**
 * 
 * @author zijiang.jl
 * 
 * 转化表达式中的部分字符
 *
 */
public interface ExpressionTransformer{
	
	/**
	 * 
	 * @param expression
	 * @return
	 */
	public String transform(String expression);
	
	
	/**
	 * 
	 * @author zijiang.jl
	 * 默认的表达式转化
	 *
	 */
	class DefaultExpressionTransformer implements ExpressionTransformer{
		@Override
		public String transform(String expression) {
			if(expression==null) expression="";
			return PlaceHolder.instance.getLeftBracketPlaceHolder()+expression
			.replace('[', '(')
			.replace(']', ')')
			.replace('{', '(')
			.replace('}', ')')
			.trim()+PlaceHolder.instance.getRightBracketPlaceHolder();
		}
		
		
	}
}
