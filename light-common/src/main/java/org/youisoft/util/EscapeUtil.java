/**
 * 
 */
package org.youisoft.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author zijiang.jl
 *
 */
public final class EscapeUtil {

	/** The Constant QUOTE. */
	public static final String JSON_BACK_SLASH = "\\\\";

	/** The Constant BACK_SLASH. */
	public static final String JSON_QUOTE = "\\\"";

	/** The Constant QUOT. */
	public static final String QUOT = "&quot;";

	/** The Constant AMP. */
	public static final String AMP = "&amp;";

	/** The Constant APOS. */
	public static final String APOS = "&apos;";

	/** The Constant GT. */
	public static final String GT = "&gt;";

	/** The Constant LT. */
	public static final String LT = "&lt;";
	
	private EscapeUtil(){}
	
	/**
	 * json字符转义，包括(",\)两个字符.
	 * 
	 * @param string
	 *            所需转义的字符串
	 * 
	 * @return 转义后的字符串
	 */
	public static String escapeAfterTrimJson(String string) {
		if (StringUtils.isEmpty(string)) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		char[] chars = string.trim().toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			switch (c) {
			case '\"':
				sb.append(JSON_QUOTE);
				break;
			case '\\':
				sb.append(JSON_BACK_SLASH);
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * xml字符转义包括(<,>,',&,")五个字符.
	 * 
	 * @param string
	 *            所需转义的字符串
	 * 
	 * @return 转义后的字符串
	 */
	public static String escapeAfterTrimXml(String string) {
		if (StringUtils.isEmpty(string)) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		char[] chars = string.trim().toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			switch (c) {
			case '<':
				sb.append(LT);
				break;
			case '>':
				sb.append(GT);
				break;
			case '\'':
				sb.append(APOS);
				break;
			case '&':
				sb.append(AMP);
				break;
			case '\"':
				sb.append(QUOT);
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * html字符转义包括(<,>,&,',")五个字符.
	 * 
	 * 实际上不应当转义AOPS（’）,但是实际的Html的代码中如果不转义AOPS，还是有
	 * 被注入的可能。所以决定要将其转义。使用（&#039;）来转义AOPS
	 * 
	 * @param string
	 *            所需转义的字符串
	 * 
	 * @return 转义后的字符串
	 */
	public static String escapeHtml(String string) {
		if (StringUtils.isEmpty(string)) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		char[] chars = string.trim().toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			switch (c) {
			case '<':
				sb.append(LT);
				break;
			case '>':
				sb.append(GT);
				break;
			case '\'': // special handling for html.
				sb.append("&#039;");
				break;
			case '&':
				sb.append(AMP);
				break;
			case '\"':
				sb.append(QUOT);
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}


}
