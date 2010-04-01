/**
 * 
 */
package com.fost.category.cache.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import com.fost.category.cache.AnnotationContext;
import com.fost.category.cache.NullValue;
import com.fost.category.cache.SsaContext;
import com.fost.category.cache.annotation.SetToCache;

/**
 * @author Janly
 *
 */
@org.aspectj.lang.annotation.Aspect
public class SetToCacheAspect extends SsaContext{
	private static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(SetToCacheAspect.class);
	@Pointcut("@annotation(com.fost.category.cache.annotation.SetToCache)")
	public void set() {}
	
	@org.aspectj.lang.annotation.Around("set()")
	public Object setToCache(final ProceedingJoinPoint pjp) throws Throwable {
		Object result=null;
		try {
			result = pjp.proceed();
			final AnnotationContext annotationContext =buildAnnotationContext(pjp,SetToCache.class);
            final String cacheKey = this.getCacheKeyProvider().generateCacheKey(annotationContext);
            
            
            
            if(annotationContext.isNoreply()){
    		    this.getCacheFactory().createCache(annotationContext).setWithNoReply(cacheKey, annotationContext.getExpiration(), result==null?new NullValue():result);
            }else{
    		    this.getCacheFactory().createCache(annotationContext).set(cacheKey, annotationContext.getExpiration(), result==null?new NullValue():result, annotationContext.getExpiration());
            }
            

		} catch (Throwable ex) {
			logger.warn(ex.getMessage());
		}
		return result;
	}
}
