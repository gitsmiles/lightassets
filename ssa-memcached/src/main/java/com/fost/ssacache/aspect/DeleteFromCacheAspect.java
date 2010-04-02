/**
 * 
 */
package com.fost.ssacache.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import com.fost.ssacache.AnnotationContext;
import com.fost.ssacache.Cache;
import com.fost.ssacache.SsaContext;
import com.fost.ssacache.annotation.DeleteFromCache;

/**
 * @author Janly
 *
 */
@org.aspectj.lang.annotation.Aspect
public class DeleteFromCacheAspect extends SsaContext{
	private static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(DeleteFromCacheAspect.class);
	@Pointcut("@annotation(com.fost.ssacache.annotation.DeleteFromCache)")
	public void deleteCache(){}
	
	@org.aspectj.lang.annotation.Around("deleteCache()")
	public Object deleteFromCache(final ProceedingJoinPoint pjp) throws Throwable {
		AnnotationContext annotationContext=null;
		Cache cache=null;
		try {
			annotationContext =buildAnnotationContext(pjp,DeleteFromCache.class);
            final String cacheKey = this.getCacheKeyProvider().generateCacheKey(annotationContext);
            cache=this.getCacheFactory().createCache(annotationContext);
            if(annotationContext.isNoreply()){
            	cache.deleteWithNoReply(cacheKey);
            }else{
            	cache.delete(cacheKey, annotationContext.getTimeOut());
            }
		} catch (Throwable ex) {
			logger.warn(ex.getMessage());
		}
		return annotationContext.isInvoke()?pjp.proceed():null;
	}
}
