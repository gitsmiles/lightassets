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
import com.fost.ssacache.annotation.AddToCache;

/**
 * @author Janly
 *
 */
@org.aspectj.lang.annotation.Aspect
public class AddToCacheAspect extends SsaContext{
	private static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(AddToCacheAspect.class);

	@Pointcut("@annotation(com.fost.ssacache.annotation.AddToCache)")
	public void addCache(){}
	
	@org.aspectj.lang.annotation.Around("addCache()")
	public Object addToCache(final ProceedingJoinPoint pjp) throws Throwable {
		Object result=null;
		Cache cache=null;
		try {
			result = pjp.proceed();
			final AnnotationContext annotationContext =buildAnnotationContext(pjp,AddToCache.class);
            final String cacheKey = this.getCacheKeyProvider().generateCacheKey(annotationContext);
            cache=this.getCacheFactory().createCache();
            if(annotationContext.isNoreply()){
            	cache.addWithNoReply(cacheKey, annotationContext.getExpiration(), result==null?new NullValue():result);
            }else{
            	cache.add(cacheKey, annotationContext.getExpiration(), result==null?new NullValue():result,annotationContext.getTimeOut());
            }
		} catch (Throwable ex) {
			logger.warn(ex.getMessage());
		}
		return result;
	
	}
}
