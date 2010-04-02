package org.youisoft;
/**
 * 
 * @author janly
 *
 */
public interface AopStyle{
	
	public final static String STYLE_BEFORE="before";
	public final static String STYLE_AFTER="after";
	public final static String STYLE_THROW="throw";
	public final static String STYLE_RETURN="return";
	public final static String MATCH_NONE="none";
	public final static String MATCH_DYNAMIC="dynamic";
	public final static String MATCH_PARAMETER="parameter";
	public final static String MATCH_EXCEPTION="exception";
	/**
	 * 
	 * @param style
	 */
	public void setStyle(String style);
	
	
	
	

}
