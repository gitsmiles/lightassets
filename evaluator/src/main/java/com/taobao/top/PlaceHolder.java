/**
 * 
 */
package com.taobao.top;


/**
 * @author zijiang.jl
 * 
 * 占位符接口
 *
 */
public final class PlaceHolder {
	
    public static final PlaceHolder instance=new PlaceHolder();
	
	private String leftBracketPlaceHolder="(";
	
	private String rightBracketPlaceHolder=")";

	private String varPlaceHolder="$";
	
	private String splitPlaceHolder=",";
	
	
	private PlaceHolder(){
		
	}

	
	//######################PLACEHOLDER#####################################
	

	public final String getVarPlaceHolder() {
		return varPlaceHolder;
	}


	public final String getLeftBracketPlaceHolder() {
		return leftBracketPlaceHolder;
	}


	public final void setLeftBracketPlaceHolder(String leftBracketPlaceHolder) {
		this.leftBracketPlaceHolder = leftBracketPlaceHolder;
	}


	public final String getRightBracketPlaceHolder() {
		return rightBracketPlaceHolder;
	}


	public final void setRightBracketPlaceHolder(String rightBracketPlaceHolder) {
		this.rightBracketPlaceHolder = rightBracketPlaceHolder;
	}


	public final void setVarPlaceHolder(String varPlaceHolder) {
		this.varPlaceHolder = varPlaceHolder;
	}

	public final String getSplitPlaceHolder() {
		return splitPlaceHolder;
	}


	public final void setSplitPlaceHolder(String splitPlaceHolder) {
		this.splitPlaceHolder = splitPlaceHolder;
	}
	
}
