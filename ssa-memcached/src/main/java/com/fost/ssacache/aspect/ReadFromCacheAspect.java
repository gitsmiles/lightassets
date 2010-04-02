/**
 * 
 */
package com.fost.ssacache.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import com.fost.ssacache.AnnotationContext;
import com.fost.ssacache.NullValue;
import com.fost.ssacache.SsaContext;
import com.fost.ssacache.annotation.ReadFromCache;

/**
 * @author Janly
 *
 */

@org.aspectj.lang.annotation.Aspect
public final class ReadFromCacheAspect extends SsaContext{
	private static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(ReadFromCacheAspect.class);
	@Pointcut("@annotation(com.fost.ssacache.annotation.ReadFromCache)")
	public void readCache(){}
	
	@org.aspectj.lang.annotation.Around("readCache()")
	public Object readFromCache(final ProceedingJoinPoint pjp) throws Throwable {
		String cacheKey=null;
		AnnotationContext annotationContext=null;
		Object result=null;
		try {
            annotationContext =buildAnnotationContext(pjp,ReadFromCache.class);
			cacheKey = this.getCacheKeyProvider().generateCacheKey(annotationContext);
			result= this.getCacheFactory().createCache(annotationContext).get(cacheKey,annotationContext.getTimeOut());
			if (result != null) {
				return (result instanceof NullValue) ? null : result;
			}
		} catch (Throwable ex) {
			logger.warn(ex.getMessage());
		}
		result = pjp.proceed();
		try {
            if(annotationContext.isNoreply()){
            	this.getCacheFactory().createCache(annotationContext).addWithNoReply(cacheKey, annotationContext.getExpiration(), result==null?new NullValue():result);
            }else{
    		    this.getCacheFactory().createCache(annotationContext).add(cacheKey, annotationContext.getExpiration(), result==null?new NullValue():result,annotationContext.getTimeOut());
            }
		} catch (Throwable ex) {
		}
		return result;
	}
}
