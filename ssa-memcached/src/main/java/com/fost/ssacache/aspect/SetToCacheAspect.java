/**
 * 
 */
package com.fost.ssacache.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import com.fost.ssacache.AnnotationContext;
import com.fost.ssacache.Cache;
import com.fost.ssacache.NullValue;
import com.fost.ssacache.SsaContext;
import com.fost.ssacache.annotation.SetToCache;

/**
 * @author Janly
 *
 */
@org.aspectj.lang.annotation.Aspect
public class SetToCacheAspect extends SsaContext{
	private static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(SetToCacheAspect.class);
	@Pointcut("@annotation(com.fost.ssacache.annotation.SetToCache)")
	public void setCache(){}
	
	@org.aspectj.lang.annotation.Around("setCache()")
	public Object setToCache(final ProceedingJoinPoint pjp) throws Throwable {
		Object result=null;
		Cache cache=null;
		try {
			result = pjp.proceed();
			final AnnotationContext annotationContext =buildAnnotationContext(pjp,SetToCache.class);
            final String cacheKey = this.getCacheKeyProvider().generateCacheKey(annotationContext);
            cache=this.getCacheFactory().createCache(annotationContext);
            
            
            if(annotationContext.isNoreply()){
            	cache.setWithNoReply(cacheKey, annotationContext.getExpiration(), result==null?new NullValue():result);
            }else{
            	cache.set(cacheKey, annotationContext.getExpiration(), result==null?new NullValue():result, annotationContext.getExpiration());
            }
            

		} catch (Throwable ex) {
			logger.warn(ex.getMessage());
		}
		return result;
	}
}
