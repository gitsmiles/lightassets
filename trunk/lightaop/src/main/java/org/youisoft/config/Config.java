package org.youisoft.config;

import java.io.IOException;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.youisoft.AopStyle;
/**
 * 
 * @author janly
 *
 */
public class Config {
	private String serviceName;
	private String serviceClassName;
	private String componentClassName;
	private String componentName;
	private String adapterClassName;
	private String adapterName;
	private boolean staticInvoke;
	private String style=AopStyle.STYLE_RETURN;
	private String match=AopStyle.MATCH_NONE;
	private final Method serviceMethod=new Method();
	private final Method componentMethod=new Method();
	private boolean catchValue=false;
	
	/**
	 * initialise the default method name,method arguments,method return,method exception of component.
	 */
	public void initServiceComponent(){
		
		//default method name
		if(this.getComponentMethod().getMethodName()==null) 
			this.getComponentMethod().setMethodName(this.getServiceMethod().getMethodName());
		
		
		if(this.componentMethod.getDescriptor()==null){
			for(java.util.Iterator it=this.getServiceMethod().getArgs().iterator();it.hasNext();){
				this.getComponentMethod().addArg(it.next().toString());
			}
			
			for(java.util.Iterator it=this.getServiceMethod().getRets().iterator();it.hasNext();){
				this.getComponentMethod().addRet(it.next().toString());
			}
		}
		
		
		if(this.componentMethod.getExceptions().length==0){
			for(java.util.Iterator it=this.getServiceMethod().getExps().iterator();it.hasNext();){
				this.getComponentMethod().addExp(it.next().toString());
			}
			
		}

	}
	
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public byte[] config(byte[] data){
		org.objectweb.asm.ClassReader cr = new org.objectweb.asm.ClassReader(data);
		return config(cr);
	}
	
	/**
	 * 
	 * @return
	 */
	public byte[] config(){
		java.io.InputStream is=this.getClass().getClassLoader().getResourceAsStream(Config.this.getServiceClassName().concat(".class"));
		org.objectweb.asm.ClassReader cr=null;
		try {
			cr = new org.objectweb.asm.ClassReader(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return config(cr);
	}
	

	/**
	 * the main method of AOP.
	 * first: method of service in configuration match the destination method.
	 * second: method of component in configuration match the service method.
	 * @return
	 */
	private byte[] config(org.objectweb.asm.ClassReader cr){
		try{
			org.objectweb.asm.ClassWriter cw=new org.objectweb.asm.ClassWriter(cr,ClassWriter.COMPUTE_MAXS);
			org.objectweb.asm.ClassAdapter ca=new org.objectweb.asm.ClassAdapter(cw){
				public MethodVisitor visitMethod(int arg0, String arg1, String arg2,String arg3, String[] arg4) {
					MethodVisitor mv=super.visitMethod(arg0, arg1, arg2, arg3, arg4);
					if(mv!=null){
						boolean match=false;
						if(arg1.equals(serviceMethod.getMethodName())){
							if(serviceMethod.getMatch().equals(AopStyle.MATCH_NONE)||
									serviceMethod.getMatch().equals(AopStyle.MATCH_DYNAMIC)
									){
								serviceMethod.setPattern(arg2);
								serviceMethod.clearExps();
								for(int i=0;arg4!=null&&i<arg4.length;i++){
									serviceMethod.addExp(arg4[i]);
								}
								match=true;
							}else if(serviceMethod.getMatch().equals(AopStyle.MATCH_PARAMETER)&&
									serviceMethod.getDescriptor().equals(arg2)){
								serviceMethod.clearExps();
								for(int i=0;arg4!=null&&i<arg4.length;i++){
									serviceMethod.addExp(arg4[i]);
								}
								match=true;
							}else if(serviceMethod.getMatch().equals(AopStyle.MATCH_EXCEPTION)&&
									serviceMethod.getDescriptor().equals(arg2)){
							
								if(arg4!=null&&serviceMethod.getExceptions().length==arg4.length){
									match=true;
									for(int i=0;i<arg4.length;i++){
										if(!arg4[i].equals(serviceMethod.getExceptions()[i])) match=false;
										
									}
								}	
							}
						}
				

						if(match){
							if(Config.this.getAdapterClassName()!=null){
								try {
									Class clazz=Class.forName(Config.this.getAdapterClassName());
									if(MethodAdapter.class.isAssignableFrom(clazz)){
										java.lang.reflect.Constructor con=clazz.getConstructor(new Class[]{MethodVisitor.class});
										mv=(MethodAdapter)con.newInstance(new Object[]{mv});
										if(AopStyle.class.isAssignableFrom(clazz)){
											((AopStyle)mv).setStyle(Config.this.getStyle());
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else{
								
								if(Config.this.getStyle().equals(AopStyle.STYLE_BEFORE)){
									
									mv=new org.objectweb.asm.MethodAdapter(mv){
										/* (non-Javadoc)
										 * @see org.objectweb.asm.MethodAdapter#visitCode()
										 */
										public void visitCode() {
											if(getServiceComponentMatch()){
												Type[] argType=Type.getArgumentTypes(Config.this.getServiceMethod().getDescriptor());
												if(Config.this.isStaticInvoke()){
													for(int i=1;i<=argType.length;i++){
														super.visitVarInsn(argType[i-1].getOpcode(Opcodes.ILOAD), i);
													}
													super.visitMethodInsn(Opcodes.INVOKESTATIC,Config.this.getComponentClassName(),
															Config.this.getComponentMethod().getMethodName(),Config.this.getComponentMethod().getDescriptor());
												}else{
													super.visitTypeInsn(Opcodes.NEW, Config.this.getComponentClassName());
													super.visitInsn(Opcodes.DUP);
													super.visitMethodInsn(Opcodes.INVOKESPECIAL,Config.this.getComponentClassName(), "<init>", "()V");
													
													for(int i=1;i<=argType.length;i++){
														super.visitVarInsn(argType[i-1].getOpcode(Opcodes.ILOAD), i);
													}
													super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,Config.this.getComponentClassName(),
															Config.this.getComponentMethod().getMethodName(),Config.this.getComponentMethod().getDescriptor());
												}
												
												super.visitInsn(Opcodes.POP);

											}
											super.visitCode();
										}
										
									};
								}
								
								if(Config.this.getStyle().equals(AopStyle.STYLE_AFTER)){
									mv=new org.objectweb.asm.MethodAdapter(mv){


										/* (non-Javadoc)
										 * @see org.objectweb.asm.MethodAdapter#visitInsn(int)
										 */
										public void visitInsn(int opcode) {
											if(opcode>=Opcodes.IRETURN&&opcode<=Opcodes.RETURN){
												if(getServiceComponentMatch()){
													Type[] argType=Type.getArgumentTypes(Config.this.getServiceMethod().getDescriptor());
													if(Config.this.isStaticInvoke()){
														for(int i=1;i<=argType.length;i++){
															super.visitVarInsn(argType[i-1].getOpcode(Opcodes.ILOAD), i);
														}
														super.visitMethodInsn(Opcodes.INVOKESTATIC,Config.this.getComponentClassName(),
																Config.this.getComponentMethod().getMethodName(),Config.this.getComponentMethod().getDescriptor());
													}else{
														super.visitTypeInsn(Opcodes.NEW, Config.this.getComponentClassName());
														super.visitInsn(Opcodes.DUP);
														super.visitMethodInsn(Opcodes.INVOKESPECIAL,Config.this.getComponentClassName(), "<init>", "()V");
														for(int i=1;i<=argType.length;i++){
															super.visitVarInsn(argType[i-1].getOpcode(Opcodes.ILOAD), i);
														}
														super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,Config.this.getComponentClassName(),
																Config.this.getComponentMethod().getMethodName(),Config.this.getComponentMethod().getDescriptor());
														
													}
													super.visitInsn(Opcodes.POP);
											
												}
											}
											super.visitInsn(opcode);
										}
									
									};
								}
								
								if(Config.this.getStyle().equals(AopStyle.STYLE_THROW)){
									mv=new org.objectweb.asm.MethodAdapter(mv){
										private int idx=0;
										private java.util.List lList=new java.util.ArrayList();
										/* (non-Javadoc)
										 * @see org.objectweb.asm.MethodAdapter#visitLabel(org.objectweb.asm.Label)
										 */
										public void visitLabel(Label arg0) {
											super.visitLabel(arg0);
											if(lList.contains(arg0)&&getServiceComponentMatch()){
												Type[] argType=Type.getArgumentTypes(Config.this.getServiceMethod().getDescriptor());
												super.visitVarInsn(Opcodes.ASTORE,idx+1);
												if(Config.this.isStaticInvoke()){
													for(int i=1;i<=argType.length;i++){
														super.visitVarInsn(argType[i-1].getOpcode(Opcodes.ILOAD), i);
													}
													if(catchValue) super.visitVarInsn(Opcodes.ALOAD,idx+1);
													super.visitMethodInsn(Opcodes.INVOKESTATIC,Config.this.getComponentClassName(),
															Config.this.getComponentMethod().getMethodName(),Config.this.getComponentMethod().getDescriptor());
												}else{
													super.visitTypeInsn(Opcodes.NEW, Config.this.getComponentClassName());
													super.visitInsn(Opcodes.DUP);
													super.visitMethodInsn(Opcodes.INVOKESPECIAL,Config.this.getComponentClassName(), "<init>", "()V");

													for(int i=1;i<=argType.length;i++){
														super.visitVarInsn(argType[i-1].getOpcode(Opcodes.ILOAD), i);
													}
													if(catchValue) super.visitVarInsn(Opcodes.ALOAD,idx+1);
													super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,Config.this.getComponentClassName(),
															Config.this.getComponentMethod().getMethodName(),Config.this.getComponentMethod().getDescriptor());
												}
												super.visitInsn(Opcodes.POP);
												super.visitVarInsn(Opcodes.ALOAD,idx+1);
											}
											
										}




										/* (non-Javadoc)
										 * @see org.objectweb.asm.MethodAdapter#visitTryCatchBlock(org.objectweb.asm.Label, org.objectweb.asm.Label, org.objectweb.asm.Label, java.lang.String)
										 */
										public void visitTryCatchBlock(Label arg0, Label arg1,Label arg2, String arg3) {
											if(arg2!=null) this.lList.add(arg2);
											super.visitTryCatchBlock(arg0, arg1, arg2, arg3);
										}




										/* (non-Javadoc)
										 * @see org.objectweb.asm.MethodAdapter#visitVarInsn(int, int)
										 */
										public void visitVarInsn(int arg0,
												int arg1) {
											if(arg0>=54&&arg0<=86){
												if(arg1>idx) idx=arg1;
											}
											super.visitVarInsn(arg0, arg1);
										}
									};
								}
								
								
								if(Config.this.getStyle().equals(AopStyle.STYLE_RETURN)){
									mv=new org.objectweb.asm.MethodAdapter(mv){
										
										private int idx=0;
										/* (non-Javadoc)
										 * @see org.objectweb.asm.MethodAdapter#visitVarInsn(int, int)
										 */
										public void visitVarInsn(int arg0,
												int arg1) {
											if(arg0>=54&&arg0<=86){
												if(arg1>idx) idx=arg1;
											}
											super.visitVarInsn(arg0, arg1);
										}
										

										public void visitInsn(int opcode) {
											if(opcode>=Opcodes.IRETURN&&opcode<=Opcodes.RETURN){
												if(getServiceComponentMatch()){
													Type[] argType=Type.getArgumentTypes(Config.this.getServiceMethod().getDescriptor());
													Type retType=Type.getReturnType(Config.this.getServiceMethod().getDescriptor());
													if(catchValue) {
														super.visitVarInsn(retType.getOpcode(Opcodes.ISTORE),idx+1);
													}else{
														super.visitInsn(Opcodes.POP);
													}
													
													if(Config.this.isStaticInvoke()){
														for(int i=1;i<=argType.length;i++){
															super.visitVarInsn(argType[i-1].getOpcode(Opcodes.ILOAD), i);
														}
														if(catchValue) super.visitVarInsn(retType.getOpcode(Opcodes.ILOAD), idx+1);
														super.visitMethodInsn(Opcodes.INVOKESTATIC,Config.this.getComponentClassName(),
																Config.this.getComponentMethod().getMethodName(),Config.this.getComponentMethod().getDescriptor());
													}else{
														super.visitTypeInsn(Opcodes.NEW, Config.this.getComponentClassName());
														super.visitInsn(Opcodes.DUP);
														super.visitMethodInsn(Opcodes.INVOKESPECIAL,Config.this.getComponentClassName(), "<init>", "()V");
														for(int i=1;i<=argType.length;i++){
															super.visitVarInsn(argType[i-1].getOpcode(Opcodes.ILOAD), i);
														}
														if(catchValue) super.visitVarInsn(retType.getOpcode(Opcodes.ILOAD), idx+1);
														super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,Config.this.getComponentClassName(),
																Config.this.getComponentMethod().getMethodName(),Config.this.getComponentMethod().getDescriptor());
														
													}

												}
											}
											super.visitInsn(opcode);
										}
									};
								}
							}
			
						}
						
					}
					return mv;
				}
			};
			cr.accept(ca, org.objectweb.asm.ClassReader.SKIP_DEBUG);
			return cw.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	private boolean getServiceComponentMatch(){
		this.initServiceComponent();
		boolean match=false;
		if(this.getComponentMethod().getMatch().equals(AopStyle.MATCH_NONE)||
				this.getComponentMethod().getMatch().equals(AopStyle.MATCH_PARAMETER)){
			if(this.getComponentMethod().getDescriptor().equals(this.getServiceMethod().getDescriptor())){
				match=true;
			}
			return match;
		}
		
		if(this.getComponentMethod().getMatch().equals(AopStyle.MATCH_DYNAMIC)){
			Type[] sargType=Type.getArgumentTypes(Config.this.getServiceMethod().getDescriptor());
			Type sretType=Type.getReturnType(Config.this.getServiceMethod().getDescriptor());
			Type[] cArgType=Type.getArgumentTypes(Config.this.getComponentMethod().getDescriptor());
			if(sargType.length==cArgType.length-1){
				if(sretType.toString().equals(cArgType[cArgType.length-1].toString())||
						Type.getDescriptor(java.lang.Throwable.class).equals(cArgType[cArgType.length-1].toString())
						){
					match=true;
					catchValue=true;
				}
	
			}
			return match;
		}
		
		
		if(this.getComponentMethod().getMatch().equals(AopStyle.MATCH_EXCEPTION)){
			if(this.getComponentMethod().getDescriptor().equals(this.getServiceMethod().getDescriptor())){
				if(this.getComponentMethod().getExceptions().length==this.getServiceMethod().getExceptions().length){
					match=true;
					for(int i=0;i<this.getComponentMethod().getExceptions().length;i++){
						if(!this.getComponentMethod().getExceptions()[i].equals(this.getServiceMethod().getExceptions()[i])) match=false;
					}
					
				}
			}

			return match;
		}
		
		
		return match;
	}
	
	/**
	 * 
	 * @param str
	 * @param begin
	 * @param end
	 * @return
	 */
	private static String substringBetween(String str,String begin,String end){
		int a=str.indexOf(begin);
		int b=str.lastIndexOf(end);
		return str.substring(a, b);
		
	}
	
	/**
	 * 
	 * @param str
	 * @param begin
	 * @return
	 */
	private static String substringBefore(String str,String begin){
		int a=str.indexOf(begin);
		return str.substring(0,a);
		
	}
	
	/**
	 * 
	 * @param str
	 * @param end
	 * @return
	 */
	private static String substringAfter(String str,String end){
		int a=str.indexOf(end);
		return str.substring(a+1);
		
	}
	
	/**
	 * 
	 * @param s
	 * @param sb
	 */
	public static void converToSimplePattern(String s,java.lang.StringBuffer sb){
		if(s.startsWith("(")) {
			converToSimplePattern(substringBetween(s, "(", ")"),sb);
			return;
		}
		
		if(s.startsWith("[")) {
			converToSimplePattern(substringAfter(s,"["),sb);
			return;
		}
		
		if(s.startsWith("L")){
			sb.append("L");
			converToSimplePattern(substringAfter(s,";"),sb);
			return;
		}
		
		if(s!=null&&s.length()>0){
			sb.append(s.substring(0,1));
			converToSimplePattern(s.substring(1),sb);
			return;
		}
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String converToPattern(String s){
		java.lang.StringBuffer sb=new java.lang.StringBuffer("");
		if(s.equals("void")) sb.append(Type.getDescriptor(void.class));
		if(s.equals("boolean")) sb.append(Type.getDescriptor(boolean.class));
		if(s.equals("int")) sb.append(Type.getDescriptor(int.class));
		if(s.equals("char")) sb.append(Type.getDescriptor(char.class));
		if(s.equals("byte")) sb.append(Type.getDescriptor(byte.class));
		if(s.equals("short")) sb.append(Type.getDescriptor(short.class));
		if(s.equals("float")) sb.append(Type.getDescriptor(float.class));
		if(s.equals("long")) sb.append(Type.getDescriptor(long.class));
		if(s.equals("double")) sb.append(Type.getDescriptor(double.class));
		if(s.equals("boolean[]")) sb.append(Type.getDescriptor(boolean[].class));
		if(s.equals("int[]")) sb.append(Type.getDescriptor(int[].class));
		if(s.equals("char[]")) sb.append(Type.getDescriptor(char[].class));
		if(s.equals("byte[]")) sb.append(Type.getDescriptor(byte[].class));
		if(s.equals("short[]")) sb.append(Type.getDescriptor(short[].class));
		if(s.equals("float[]")) sb.append(Type.getDescriptor(float[].class));
		if(s.equals("long[]")) sb.append(Type.getDescriptor(long[].class));
		if(s.equals("double[]")) sb.append(Type.getDescriptor(double[].class));
		if(s.equals("boolean[][]")) sb.append(Type.getDescriptor(boolean[][].class));
		if(s.equals("int[][]")) sb.append(Type.getDescriptor(int[][].class));
		if(s.equals("char[][]")) sb.append(Type.getDescriptor(char[][].class));
		if(s.equals("byte[][]")) sb.append(Type.getDescriptor(byte[][].class));
		if(s.equals("short[][]")) sb.append(Type.getDescriptor(short[][].class));
		if(s.equals("float[][]")) sb.append(Type.getDescriptor(float[][].class));
		if(s.equals("long[][]")) sb.append(Type.getDescriptor(long[][].class));
		if(s.equals("double[][]")) sb.append(Type.getDescriptor(double[][].class));
		
		if(!sb.toString().equals("")) return sb.toString();

		if(s.endsWith("[][]")) {
			sb.append("[[L"+sb.append(substringBefore(s, "[][]")));
		}else if(s.endsWith("[]")) {
			sb.append("[L"+sb.append(substringBefore(s, "[]")));
		}else{
			sb.append("L"+s);
		}
		sb.append(";");

		return sb.toString();
	}
	
	
	
	
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the serviceClassName
	 */
	public String getServiceClassName() {
		return serviceClassName;
	}

	/**
	 * @param serviceClassName the serviceClassName to set
	 */
	public void setServiceClassName(String serviceClassName) {
		if(serviceClassName!=null) serviceClassName=serviceClassName.replace('.', '/');
 		this.serviceClassName = serviceClassName;
	}

	/**
	 * @return the componentClassName
	 */
	public String getComponentClassName() {
		return componentClassName;
	}

	/**
	 * @param componentClassName the componentClassName to set
	 */
	public void setComponentClassName(String componentClassName) {
		if(componentClassName!=null) componentClassName=componentClassName.replace('.', '/');
		this.componentClassName = componentClassName;
	}

	/**
	 * @return the componentName
	 */
	public String getComponentName() {
		return componentName;
	}

	/**
	 * @param componentName the componentName to set
	 */
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	
	
	
	/**
	 * @return the adapterClassName
	 */
	public String getAdapterClassName() {
		return adapterClassName;
	}


	/**
	 * @param adapterClassName the adapterClassName to set
	 */
	public void setAdapterClassName(String adapterClassName) {
		this.adapterClassName = adapterClassName;
	}


	/**
	 * @return the adapterName
	 */
	public String getAdapterName() {
		return adapterName;
	}


	/**
	 * @param adapterName the adapterName to set
	 */
	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}
	


	/**
	 * @return the match
	 */
	public String getMatch() {
		return match;
	}



	/**
	 * @param match the match to set
	 */
	public void setMatch(String match) {
		this.match = match;
	}



	/**
	 * @return the staticInvoke
	 */
	public boolean isStaticInvoke() {
		return staticInvoke;
	}



	/**
	 * @param staticInvoke the staticInvoke to set
	 */
	public void setStaticInvoke(boolean staticInvoke) {
		this.staticInvoke = staticInvoke;
	}



	/**
	 * @return the serviceMethod
	 */
	public Method getServiceMethod() {
		return serviceMethod;
	}


	/**
	 * @return the componentMethod
	 */
	public Method getComponentMethod() {
		return componentMethod;
	}





	/**
	 * 
	 * @author janly
	 *
	 */
	class Method{
		private String methodName;
		private String pattern;
		private String match;
		private final java.util.List args=new java.util.ArrayList();
		private final java.util.List rets=new java.util.ArrayList();
		private final java.util.List exps=new java.util.ArrayList();
		
		
		public void clearExps(){
			this.exps.clear();
		}
		
		private String convertArgsToPattern(){
			if(methodName==null) return null;
			if(this.rets.isEmpty()) return null;
			
			java.lang.StringBuffer sb=new java.lang.StringBuffer("(");
			for(java.util.Iterator it=this.args.iterator();it.hasNext();){
				sb.append(Config.converToPattern(it.next().toString()));
			}
			sb.append(")");
			
			for(java.util.Iterator it=this.rets.iterator();it.hasNext();){
				sb.append(Config.converToPattern(it.next().toString()));
			}

			return sb.toString();
		}	
		
		public String[] getExceptions(){
			return (String[])this.exps.toArray(new String[]{});
		}
		
		boolean addArg(String arg){
			return args.add(arg.replace('.', '/'));
		}
		
		boolean addRet(String ret){
			return rets.add(ret.replace('.', '/'));
		}
		
		boolean addExp(String exp){
			return exps.add(exp.replace('.', '/'));
		}
		
		/**
		 * @return the pattern
		 */
		public String getPattern() {
			return pattern;
		}
		/**
		 * @param pattern the pattern to set
		 */
		public void setPattern(String pattern) {
			this.pattern = pattern;
			this.args.clear();
			this.rets.clear();
			Type[] argsType=Type.getArgumentTypes(pattern);
			Type retsType=Type.getReturnType(pattern);
			for(int i=0;i<argsType.length;i++){
				this.args.add(argsType[i].getClassName());
			}
			this.rets.add(retsType.getClassName());
		}
		/**
		 * @return the methodName
		 */
		public String getMethodName() {
			return methodName;
		}
		/**
		 * @param methodName the methodName to set
		 */
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		/**
		 * @return the descriptor
		 */
		public String getDescriptor() {
			if(this.pattern!=null) return this.pattern;
			return this.convertArgsToPattern();
		}

		/**
		 * @return the args
		 */
		public java.util.List getArgs() {
			return args;
		}

		/**
		 * @return the rets
		 */
		public java.util.List getRets() {
			return rets;
		}

		/**
		 * @return the exps
		 */
		public java.util.List getExps() {
			return exps;
		}
		
		
		

		/**
		 * @return the match
		 */
		public String getMatch() {
			if(this.match==null) return Config.this.getMatch();
			return match;
		}

		/**
		 * @param match the match to set
		 */
		public void setMatch(String match) {
			this.match = match;
		}
	
		
	}

}

