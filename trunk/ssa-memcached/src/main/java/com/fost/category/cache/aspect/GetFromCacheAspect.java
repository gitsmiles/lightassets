/**
 * 
 */
package com.fost.category.cache.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import com.fost.category.cache.AnnotationContext;
import com.fost.category.cache.NullValue;
import com.fost.category.cache.SsaContext;
import com.fost.category.cache.annotation.GetFromCache;

/**
 * @author Janly
 *
 */

@org.aspectj.lang.annotation.Aspect
public final class GetFromCacheAspect extends SsaContext{
	private static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(GetFromCacheAspect.class);
	@Pointcut("@annotation(com.fost.category.cache.annotation.GetFromCache)")
	public void get() {}
	
	@org.aspectj.lang.annotation.Around("get()")
	public Object getFromCache(final ProceedingJoinPoint pjp) throws Throwable {
		Object result=null;
		try {
			final AnnotationContext annotationContext =buildAnnotationContext(pjp,GetFromCache.class);
			final String cacheKey = this.getCacheKeyProvider().generateCacheKey(annotationContext);
			result = this.getCacheFactory().createCache(annotationContext).get(cacheKey, annotationContext.getTimeOut());
			if(annotationContext.isInvoke()) pjp.proceed();
		} catch (Throwable ex) {
			logger.warn(ex.getMessage());
		}
		return (result instanceof NullValue) ? null : result;
	}
}
