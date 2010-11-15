package com.taobao.top.bracket;

import java.util.Stack;

import com.taobao.top.CalContext;
import com.taobao.top.EvaluateException;
import com.taobao.top.function.Function;
import com.taobao.top.operator.Operator;


/**
 * 
 * @author zijiang.jl
 *
 * @param <R>
 * @param <P>
 */
public abstract class AbstractPriorityBracket<R,P> extends AbstractBracket<R,P>  {
	
	
	private static java.lang.ThreadLocal<Stack<Operator<?>>> localStack=new java.lang.ThreadLocal<Stack<Operator<?>>>();

	private java.util.List<java.util.List<Operator<?>>> list;
	
	public AbstractPriorityBracket(){
		list=new java.util.ArrayList<java.util.List<Operator<?>>>();
	}
	
	@Override
	public void init() {
		super.init();
		String[] expression=this.getExpression();
		for(String exp:expression){
			list.add(toBoland(toMiddleList(exp)));
		}
	}
	

	@Override
	public void reInit() {
		this.list=null;
		this.init();
		
	}

	private static void checkLocal(){
		if(localStack.get()==null){
			localStack.set(new Stack<Operator<?>>());
		}
		localStack.get().clear();
	}
	
	

	/**
	 * 
	 * 
	 * 2+3+4+(1)+(2)+(3);
	 * 
	 * 
	 */
	@Override
	public Operator<?> calculate(CalContext<?> context) throws EvaluateException{
		if(!this.isInitilize()) throw new EvaluateException("please initilize bracket!");
		Operator<?> result=Operator.nullInstance;
		
		Operator<?>[] operator=new Operator<?>[list.size()];
		for(int i=0;i<list.size();i++){
			java.util.List<Operator<?>> li=list.get(i);
			checkLocal();
			for(Operator<?> op:li){
				if(op instanceof OperateOperator){
					OperateOperator oo=(OperateOperator)op;
					Operator<?>[] param=new Operator<?>[oo.value().getOpers()];
					Function fun=this.getFunctionRegistry().findFunction(oo.value().name());

					for(int j=oo.value().getOpers()-1;j>=0;j--){
						param[j]=localStack.get().pop();
						
					}
					
					if(fun!=null&&fun.supportOperator(context,param)) {
						Operator<?> p=fun.caculate(context,param);
						localStack.get().push(p);
					}else{
						localStack.get().push(Operator.nullInstance);
					}
					
				}else{
					localStack.get().push(this.convertPlaceHolder(op));
				}
			}
			if(!localStack.get().isEmpty()){
				operator[i]=localStack.get().pop();
			}else{
				operator[i]=Operator.nullInstance;
			}
			
		}
		

		if(this.getfName()!=null){
			Function fun=this.getFunctionRegistry().findFunction(this.getfName());
			if(fun!=null&&fun.supportOperator(context,operator)) result=fun.caculate(context,operator);
		}else{
			result=operator[0];
		}
		
		return result;
	}
}
