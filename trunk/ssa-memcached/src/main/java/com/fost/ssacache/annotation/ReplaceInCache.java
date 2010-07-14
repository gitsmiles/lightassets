/**
 * 
 */
package com.fost.ssacache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Janly
 *
 *
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReplaceInCache {

	public String namespace() default "root";
	
	public int timeout() default 0;
	
	public boolean noreply() default true;
	
	public String assignedKey() default "";
	
	public int expiration() default 0;
	
}
