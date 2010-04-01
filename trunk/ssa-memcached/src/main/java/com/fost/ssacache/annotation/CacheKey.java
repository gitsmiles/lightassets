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
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.ANNOTATION_TYPE})
public @interface CacheKey {
	
	public String beanName() default "";
	
	public String name() default "";
	
	public int order() default 0;
}
