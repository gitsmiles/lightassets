/**
 * 
 */
package com.fost.ssacache.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import com.fost.ssacache.AnnotationContext;
import com.fost.ssacache.NullValue;
import com.fost.ssacache.SsaContext;
import com.fost.ssacache.annotation.GetFromCache;

/**
 * @author Janly
 *
 */

@org.aspectj.lang.annotation.Aspect
public final class GetFromCacheAspect extends SsaContext{
	private static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(GetFromCacheAspect.class);
	@Pointcut("@annotation(com.fost.ssacache.annotation.GetFromCache)")
	public void getCache(){}
	
	@org.aspectj.lang.annotation.Around("getCache()")
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
