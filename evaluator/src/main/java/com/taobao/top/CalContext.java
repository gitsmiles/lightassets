/**
 * 
 */
package com.taobao.top;

/**
 * @author zijiang.jl
 *
 */
public final class CalContext<C> {
	
	private C context;

	public final C getContext() {
		return context;
	}

	public final void setContext(C context) {
		this.context = context;
	}
}
