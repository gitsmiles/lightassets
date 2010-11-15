/**
 * 
 */
package com.taobao.top.bracket;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.top.BracketVisitor;
import com.taobao.top.CalContext;
import com.taobao.top.EvalConst;
import com.taobao.top.EvaluateException;
import com.taobao.top.OpEnum;
import com.taobao.top.PlaceHolder;
import com.taobao.top.function.FunctionRegistry;
import com.taobao.top.operator.Operator;
import com.taobao.top.operator.base.DoubleOperator;
import com.taobao.top.operator.base.StringOperator;

/**
 * 
 * @author zijiang.jl
 *
 * @param <T>
 * @param <R>
 * @param <P>
 */
public abstract class AbstractBracket<R,P> implements Bracket<R,P> {
	protected static final Log logger=LogFactory.getLog(Bracket.class);

	private static java.lang.ThreadLocal<java.util.List<Operator<?>>> localValue=new java.lang.ThreadLocal<java.util.List<Operator<?>>>();
	
	private int leftIndex=-1;
	
	private int rightIndex=-1;
	
	private int funIndex=-1;

	private Object source;
	
	private String content;
	
	private String fName;
	
	protected P property;
	
	protected R resource;
	
	protected AtomicBoolean initilize;

	private java.util.List<Bracket<R,P>> brackets;
	
	private Bracket<R,P> parentBracket;
	
	private FunctionRegistry functionRegistry;
	
	public AbstractBracket(){
		initilize=new AtomicBoolean(false);
		brackets=new java.util.ArrayList<Bracket<R,P>>();
	}
	
	@Override
	public void init() {
		initilize.set(true);
		
	}

	public final boolean isInitilize() {
		return this.initilize.get();
	}


	@Override
	public void accept(CalContext<?> context,BracketVisitor<R,P> visitor) throws EvaluateException{
		logger.debug("accept visitor......");
		this.setProperty(visitor.getProperty());
		this.setResource(visitor.getResource());
		this.checkLocalValue();
		for(int i=0;i<this.childSize();i++){
			this.getChildtBracket(i).accept(context,visitor);
			localValue.get().add(visitor.getValue());
		}
		logger.debug("begin visit......");
		visitor.visit(context,this);
		logger.debug("end visit......");
	}
	
	private void checkLocalValue(){
		if(this.childSize()>0){
			if(localValue.get()==null){
				localValue.set(new java.util.ArrayList<Operator<?>>());
			}
			localValue.get().clear();
		}
	}
	
	

	protected Operator<?> convertPlaceHolder(Operator<?> value) throws EvaluateException{
		value=this.resolveChildBracket(value);
		value=this.resolveVarPlaceHolder(value);
		return value;
	}

	protected Operator<?> resolveChildBracket(Operator<?> value) throws EvaluateException{ 
		if(value instanceof StringOperator){
			StringOperator so=(StringOperator)value;
			if(so.value().startsWith(PlaceHolder.instance.getLeftBracketPlaceHolder())&&
					so.value().endsWith(PlaceHolder.instance.getRightBracketPlaceHolder())){
				int idx=Integer.parseInt(so.value().substring(1,so.value().length()-1));
				value=this.getValueByIndex(idx);
			}	
		}
		
		return value;
	}
	
	
	protected abstract Operator<?> resolveVarPlaceHolder(Operator<?> value) throws EvaluateException;
	
	
	
	
	//=================================

	/**
	 * 转化为中序序列
	 * @param expression
	 * @return
	 */
	public static java.util.List<String> toMiddleList(String expression){
		java.util.StringTokenizer st=new java.util.StringTokenizer(expression,OpEnum.getOpsString(),true);
		Stack<String> exp = new Stack<String>();
		exp.push("");
		boolean isFirst=true;
	    while (st.hasMoreTokens()) {
	    	String token=st.nextToken().trim();
	    	if(isFirst&&OpEnum.isOp(EvalConst.SHARP+token)){
    			exp.push(EvalConst.SHARP+token);
	    	}else{
		    	String pre=exp.peek();
		    	String temp=pre+token;
		    	
		    	if(OpEnum.isOp(temp)){
		    		exp.pop();
		    		exp.push(temp);
		    	}else if(OpEnum.isOp(pre)&&OpEnum.isOpString(token)){
		    		exp.push(EvalConst.SHARP+token);
		    	}else{
		    		exp.push(token);
		    	}
	    	}
	    	
	    	if(isFirst) isFirst=false;
	    }
	    exp.remove(0);  
	    return exp;
	}
	

	/**
	 * 中序转化为逆波兰式
	 * @param in
	 * @return
	 */
	public static java.util.List<Operator<?>> toBoland(List<String> in){
		Stack<OpEnum> stack=new Stack<OpEnum>();
		java.util.List<Operator<?>> list=new java.util.ArrayList<Operator<?>>();
		stack.push(OpEnum.EMP);
		for(String str:in){
			OpEnum op=OpEnum.fromString(str);
			 if(op!=null){
				 while(op.getPriority()<stack.peek().getPriority()){
					 list.add(new OperateOperator().setValue(stack.pop()));
				 }
				 stack.push(op);
			 }else{
				 if(org.apache.commons.lang.math.NumberUtils.isNumber(str)){
					 list.add(new DoubleOperator(str));
				 }else{
					 list.add(new StringOperator(str));
				 }
				 
			 }
		}
		while(!stack.isEmpty()){
			list.add(new OperateOperator().setValue(stack.pop()));
		}
		list.remove(list.size()-1);
		return list;
	}

	//====================================
	
	@Override
	public Bracket<R, P> getParentBracket() {
		return this.parentBracket;
	}

	@Override
	public void setParentBracket(Bracket<R, P> parentBracket) {
		this.parentBracket=parentBracket;
		
	}
	
	@Override
	public void addChildBracket(Bracket<R,P> bracket) {
		this.brackets.add(bracket);
	}
	

	@Override
	public int childSize() {
		return this.brackets.size();
	}

	@Override
	public Bracket<R, P> getChildtBracket(int index) {
		return this.brackets.get(index);
	}
	
	@Override
	public String getfName() {
		return fName;
	}

	@Override
	public void setfName(String fName) {
		this.fName = fName;
	}

	public P getProperty() {
		return property;
	}

	public void setProperty(P property) {
		this.property = property;
	}

	public R getResource() {
		return resource;
	}

	public void setResource(R resource) {
		this.resource = resource;
	}
	
	

	@Override
	public int getFunIndex() {
		return this.funIndex;
	}

	@Override
	public void setFunIndex(int funIndex) {
		this.funIndex=funIndex;
	}

	@Override
	public void setLeftIndex(int idx) {
		this.leftIndex=idx;
		
	}

	@Override
	public int getLeftIndex() {
	
		return this.leftIndex;
	}

	@Override
	public int getRightIndex() {
	
		return this.rightIndex;
	}

	@Override
	public void setRightIndex(int idx) {
		this.rightIndex=idx;
		
	}
	

	@Override
	public void setSource(Object obj) {
		this.source=obj;
		
	}


	@Override
	public Object getSource() {
		return this.source;
	}


	
	public Operator<?> getValueByIndex(int idx){
		if(idx>=localValue.get().size()||idx<0) throw new java.lang.RuntimeException("Placeholder index is error!");
		return localValue.get().get(idx);
	}
	
	
	public String getContent() {
		if(this.content!=null) return this.content;
		return this.getContentFromSource();
	}
	
	public String[] getExpression(){
		return this.getContent().split(PlaceHolder.instance.getSplitPlaceHolder());
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	protected String getContentFromSource(){
		return null;
	}

	public final FunctionRegistry getFunctionRegistry() {
		return functionRegistry;
	}

	public final void setFunctionRegistry(FunctionRegistry functionRegistry) {
		this.functionRegistry = functionRegistry;
	}

}
