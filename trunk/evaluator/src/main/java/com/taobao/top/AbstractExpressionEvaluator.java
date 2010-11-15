/**
 * 
 */
package com.taobao.top;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.top.BracketVisitor;
import com.taobao.top.ExpressionEvaluator;
import com.taobao.top.bracket.Bracket;
import com.taobao.top.function.AbstractFunctionRegistry;
import com.taobao.top.function.Function;
import com.taobao.top.function.FunctionRegistry;

/**
 * 
 * @author zijiang.jl
 *
 * @param <R>
 * @param <P>
 */
public abstract class AbstractExpressionEvaluator<R,P> implements ExpressionEvaluator<R,P> {
	protected static final Log logger=LogFactory.getLog(ExpressionEvaluator.class);

	
	private String sourceStr;
	
	private String expression;
	
	private String bracketClassName;
	
	private Bracket<R,P> bracket;
	
	private BracketVisitor<R, P> bracketVisitor;
	
	private ExpressionTransformer expressionTransformer;
	
	private FunctionRegistry functionRegistry;
	
	private AtomicBoolean initilize;
	
	public AbstractExpressionEvaluator(String str){
		initilize=new AtomicBoolean(false);
		this.sourceStr=str;
		functionRegistry=new AbstractFunctionRegistry(){
			
		};
	}
	
	@Override
	public void init() {
		logger.debug("init......");
		if(this.bracketClassName==null) throw new java.lang.RuntimeException("Please set bracket class");
		if(this.bracketVisitor==null) this.bracketVisitor=new BracketVisitor<R,P>();
		if(this.expressionTransformer==null) this.expressionTransformer=new ExpressionTransformer.DefaultExpressionTransformer();
		this.transformStr();
		this.bracket=this.splitBracket(this.getExpression());
		this.initilize.set(true);
	}
	
	public final boolean isInitilize() {
		return this.initilize.get();
	}
	
	
	
	private void transformStr(){
		expression=this.expressionTransformer.transform(sourceStr);
	}
	
	protected String getExpression(){
		if(this.expression!=null) return this.expression;
		return this.sourceStr;
	}
	
	
	protected Bracket<R,P> splitBracket(String str){
		Bracket<R,P> bracket=null;
		Bracket<R,P> preBracket=null;
		char[] strChar=str.toCharArray();
		java.util.Stack<Bracket<R,P>> stack=new java.util.Stack<Bracket<R,P>>();

		int opIndex=-1;
		for(int i=0;i<strChar.length;i++){
			if(OpEnum.isOpChar(strChar[i])) opIndex=i;
			if(EvalConst.LEFTBRACKET==strChar[i]){
				bracket=this.getNewBracketObject();
				bracket.setSource(str);
				bracket.setLeftIndex(i);
				bracket.setFunctionRegistry(this.getFunctionRegistry());
				
				if(!stack.isEmpty()) {
					preBracket=stack.peek();
					bracket.setParentBracket(preBracket);
					preBracket.addChildBracket(bracket);
				}
				
				if(opIndex>=0&&i-opIndex>1) {
					bracket.setFunIndex(opIndex+1);
					bracket.setfName(str.substring(opIndex+1,i));
				}
				
				if(opIndex<0&&bracket.getParentBracket()!=null){
					bracket.setFunIndex(bracket.getParentBracket().getLeftIndex()+1);
					bracket.setfName(str.substring(bracket.getParentBracket().getLeftIndex()+1,i));
				}
				
				stack.push(bracket);
				opIndex=-1;
				
			}else if(EvalConst.RIGHTBRACKET==strChar[i]){
				bracket=stack.pop();
				bracket.setRightIndex(i);
				
				//========================
				java.lang.StringBuilder sb=new java.lang.StringBuilder("");
				int cnt=bracket.childSize();
				int a=bracket.getLeftIndex()+1;
				int b;
				for(int j=0;j<cnt;j++){
					if(bracket.getChildtBracket(j).getfName()!=null){
						b=bracket.getChildtBracket(j).getFunIndex();
					}else{
						b=bracket.getChildtBracket(j).getLeftIndex();
					}
					sb.append(str.substring(a,b));
					sb.append(PlaceHolder.instance.getLeftBracketPlaceHolder()+j+PlaceHolder.instance.getRightBracketPlaceHolder());
					a=bracket.getChildtBracket(j).getRightIndex()+1;
				}
				b=bracket.getRightIndex();
				sb.append(str.substring(a,b));
				bracket.setContent(sb.toString());
				
				bracket.init();
			}
		
		}
		if(!stack.isEmpty()) throw new java.lang.RuntimeException("The expression is error,pls check the bracket!");
		return bracket;
	}

	@Override
	public void reInit() {
		this.destory();
		this.init();
	}
	
	

	@Override
	public void start() {
		
		
	}

	@Override
	public void reStart() {
		
		
	}

	@Override
	public void stop() {
		
		
	}

	@Override
	public void destory() {
		this.bracketVisitor=null;
		this.expressionTransformer=null;
		this.bracketClassName=null;
		initilize.set(false);
		
	}

	@SuppressWarnings("unchecked")
	private Bracket<R,P> getNewBracketObject(){
		try {
			return (Bracket<R,P>)this.getClass().getClassLoader().loadClass(this.getBracketClassName()).newInstance();
		} catch (Exception e) {
			throw new java.lang.RuntimeException("Can't create bracket object", e);
		}
	}

	@Override
	public Object evaluate(CalContext<?> context,R resource,P property) throws EvaluateException{
		if(!this.isInitilize()) throw new EvaluateException("please initilize ExpressionEvaluator!");
		this.bracketVisitor.setProperty(property);
		this.bracketVisitor.setResource(resource);
		this.bracket.accept(context,this.bracketVisitor);
		return this.bracketVisitor.getValue().value();
	}
	
	
	public final String getBracketClassName() {
		return bracketClassName;
	}

	public final void setBracketClassName(String bracketClassName) {
		this.bracketClassName = bracketClassName;
	}

	@Override
	public BracketVisitor<R, P> getBracketVisitor() {
		
		return bracketVisitor;
	}

	@Override
	public void setBracketVisitor(BracketVisitor<R, P> bracketVisitor) {
		this.bracketVisitor = bracketVisitor;
	}
	

	@Override
	public final ExpressionTransformer getExpressionTransformer() {
		return expressionTransformer;
	}

	@Override
	public final void setExpressionTransformer(ExpressionTransformer expressionTransformer) {
		this.expressionTransformer = expressionTransformer;
	}

	@Override
	public void registerFunction(Function function) {
		this.functionRegistry.registrer(function);
		
	}

	@Override
	public void unRegisterFunction(String functionName) {
		this.functionRegistry.unRegistrer(functionName);
		
	}

	public final FunctionRegistry getFunctionRegistry() {
		return functionRegistry;
	}

	public final void setFunctionRegistry(FunctionRegistry functionRegistry) {
		this.functionRegistry = functionRegistry;
	}


	
}
