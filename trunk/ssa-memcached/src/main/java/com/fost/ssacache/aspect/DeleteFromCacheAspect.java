/**
 * 
 */
package com.fost.ssacache.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import com.fost.ssacache.AnnotationContext;
import com.fost.ssacache.SsaContext;
import com.fost.ssacache.annotation.DeleteFromCache;

/**
 * @author Janly
 *
 */
@org.aspectj.lang.annotation.Aspect
public class DeleteFromCacheAspect extends SsaContext{
	private static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(DeleteFromCacheAspect.class);
	@Pointcut("@annotation(com.fost.category.ssacache.annotation.DeleteFromCache)")
	public void delete() {}
	
	@org.aspectj.lang.annotation.Around("delete()")
	public Object deleteFromCache(final ProceedingJoinPoint pjp) throws Throwable {
		AnnotationContext annotationContext=null;
		try {
			annotationContext =buildAnnotationContext(pjp,DeleteFromCache.class);
            final String cacheKey = this.getCacheKeyProvider().generateCacheKey(annotationContext);
            if(annotationContext.isNoreply()){
            	this.getCacheFactory().createCache(annotationContext).deleteWithNoReply(cacheKey);
            }else{
            	this.getCacheFactory().createCache(annotationContext).delete(cacheKey, annotationContext.getTimeOut());
            }
		} catch (Throwable ex) {
			logger.warn(ex.getMessage());
		}
		return annotationContext.isInvoke()?pjp.proceed():null;
	}
}
