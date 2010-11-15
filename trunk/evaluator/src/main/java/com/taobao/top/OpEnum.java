/**
 * 
 */
package com.taobao.top;


/**
 * 
 * @author zijiang.jl
 * 操作符的字符数最大只能为2
 * 一元操作符符号在左边
 *
 */
public enum OpEnum {
	
	EMP(EvalConst.SHARP,0,0),

	OR("|",5,2),
	ORR("||",5,2),
	XOR("^",5,2),
	SAM("",5,2),
	XORR("^^",5,2),
	
	
	AND("&",10,2),
	ANDD("&&",10,2),
	
	
	EQQ("==",15,2),
	EQ("=",15,2),
	NEQ("!=",15,2),
	
	GRT(">",20,2),
	LES("<",20,2),
	GRE(">=",20,2),
	LEE("<=",20,2),
	GRL(">>",20,2),
	LEL("<<",20,2),
	
	
	ADD("+",30,2),
	SUB("-",30,2),
	
	MUL("*",35,2),
	DIV("/",35,2),
	MOD("%",35,2),
	
	
	PLUS(EvalConst.SHARP+"+",100,1),
	MINUS(EvalConst.SHARP+"-",100,1),
	NO(EvalConst.SHARP+"~",100,1);
	
	
	
	private String op;
	private int priority;
	private int opers;

	
	
	private OpEnum(String op,int priority,int opers){
		this.op=op;
		this.priority=priority;
		this.opers=opers;
	}
	
	public static boolean isOpString(String op){
		return getOpsString().indexOf(op)!=-1;
	}
	
	public static boolean isOpChar(char op){
		return getOpsString().indexOf(op)!=-1;
	}
	
	public static boolean isOp(String op){
		return fromString(op)!=null;
	}

	
	/**
	 * 
	 * @param op
	 * @return
	 */
	public static OpEnum fromString(String op){
		
		if(ADD.getOp().equals(op)) return ADD;
		if(SUB.getOp().equals(op)) return SUB;
		
		if(MUL.getOp().equals(op)) return MUL;
		if(DIV.getOp().equals(op)) return DIV;
		if(MOD.getOp().equals(op)) return MOD;
		
		
		if(OR.getOp().equals(op)) return OR;
		if(ORR.getOp().equals(op)) return ORR;
		if(XOR.getOp().equals(op)) return XOR;
		
		
		if(AND.getOp().equals(op)) return AND;
		if(ANDD.getOp().equals(op)) return ANDD;
		
		
		if(EQQ.getOp().equals(op)) return EQQ;
		if(EQ.getOp().equals(op)) return EQ;
		if(NEQ.getOp().equals(op)) return NEQ;
		
		if(GRT.getOp().equals(op)) return GRT;
		if(LES.getOp().equals(op)) return LES;
		if(GRE.getOp().equals(op)) return GRE;
		if(LEE.getOp().equals(op)) return LEE;
		
		if(GRL.getOp().equals(op)) return GRL;
		if(LEL.getOp().equals(op)) return LEL;
		
		
		//
		if(PLUS.getOp().equals(op)) return PLUS;
		if(MINUS.getOp().equals(op)) return MINUS;
		if(NO.getOp().equals(op)) return NO;
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public static java.util.Set<String> getOpsSet(){
		OpEnum[] ops=OpEnum.values();
		java.util.Set<String> set=new java.util.HashSet<String>(ops.length);
		for(int i=0;i<ops.length;i++){
			set.add(ops[i].getOp().replace(EvalConst.SHARP, ""));
		}
		return set;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getOpsString(){
		java.lang.StringBuilder sb=new java.lang.StringBuilder("");
		OpEnum[] ops=OpEnum.values();
		for(int i=0;i<ops.length;i++){
			sb.append(ops[i].getOp().replace(EvalConst.SHARP, ""));
		}
		return sb.toString();
	}
	
	
	public String getOp(){
		return this.op;
	}
	
	public int getPriority(){
		return this.priority;
	}


	public final int getOpers() {
		return opers;
	}
	
	


}
