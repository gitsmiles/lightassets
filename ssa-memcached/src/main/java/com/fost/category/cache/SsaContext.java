
package com.fost.category.cache;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.fost.category.cache.annotation.CacheKey;

/**
 * @author Janly
 *
 */
public abstract class SsaContext implements org.springframework.context.ApplicationContextAware,org.springframework.beans.factory.InitializingBean{
	protected static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(SsaContext.class);
	protected static final String SEPARATOR = ":";
	private CacheFactory cacheFactory;
	private CacheKeyProvider cacheKeyProvider;
	private ApplicationContext applicationContext;
	
    @Override
	public void afterPropertiesSet() throws Exception {
    	logger.info("init...."+this.getClass().getName());

	}
    

	protected static Method getMethodToCache(final JoinPoint jp) throws NoSuchMethodException {
		final org.aspectj.lang.Signature sig = jp.getSignature();
		if (!(sig instanceof org.aspectj.lang.reflect.MethodSignature)) {
			throw new RuntimeException("This annotation is only valid on a method.");
		}
		final org.aspectj.lang.reflect.MethodSignature msig = (org.aspectj.lang.reflect.MethodSignature) sig;
		final Object target = jp.getTarget();
		return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
	}
	
	
    protected static <T extends Annotation> AnnotationContext buildAnnotationContext(final JoinPoint jp,final Class<T> expectedAnnotationClass){
    	final AnnotationContext data=new AnnotationContext();
    	try{
    	   	Method mh=getMethodToCache(jp);
        	java.lang.annotation.Annotation annotation=mh.getAnnotation(expectedAnnotationClass);
    		populateClassName(data, annotation, expectedAnnotationClass);
    		populateAnnotation(data, annotation, expectedAnnotationClass, mh.getName());
    		populateParamIndex(data,jp, CacheKey.class, mh);
    	}catch(java.lang.Throwable t){
    		throw new java.lang.RuntimeException("parse annotation fail");
    	}
    	return data;
    }
    
    
    
    protected static void populateClassName(final AnnotationContext data,
			final Annotation annotation, final Class<?> expectedAnnotationClass) {
		if (annotation == null) {
			throw new InvalidParameterException(String.format(
					"No annotation of type [%s] found.",
					expectedAnnotationClass.getName()));
		}

		final Class<?> clazz = annotation.annotationType();
		if (!expectedAnnotationClass.equals(clazz)) {
			throw new InvalidParameterException(
					String
							.format(
									"No annotation of type [%s] found, class was of type [%s].",
									expectedAnnotationClass.getName(), clazz
											.getName()));
		}
		data.setClassName(clazz.getName());
	}
    
    protected static void populateAnnotation(final AnnotationContext data,
			final Annotation annotation, final Class<?> expectedAnnotationClass,
			final String targetMethodName) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
    	
		final Method cacheNameMethod = expectedAnnotationClass.getDeclaredMethod("cacheName", new Class[]{});
		final String cacheName = (String)cacheNameMethod.invoke(annotation,new Object[]{});
		data.setCacheName(cacheName);
		
    	
		final Method namespaceMethod = expectedAnnotationClass.getDeclaredMethod("namespace", new Class[]{});
		final String namespace = (String) namespaceMethod.invoke(annotation,new Object[]{});
		data.setNamespace(namespace);
		
        final Method assignKeyMethod = expectedAnnotationClass.getDeclaredMethod("assignedKey", new Class[]{});
        final String assignKey = (String) assignKeyMethod.invoke(annotation, new Object[]{});
        data.setAssignedKey(assignKey);
        
        final Method expirationMethod = expectedAnnotationClass.getDeclaredMethod("expiration", new Class[]{});
        final int expiration = (Integer) expirationMethod.invoke(annotation, new Object[]{});
        if (expiration < 0) {
            throw new InvalidParameterException(String.format(
                    "Expiration for annotation [%s] must be 0 or greater on [%s]",
                    expectedAnnotationClass.getName(),
                    targetMethodName
            ));
        }
        data.setExpiration(expiration);
        
        final Method timeoutMethod = expectedAnnotationClass.getDeclaredMethod("timeout", new Class[]{});
        final int timeout = (Integer) timeoutMethod.invoke(annotation, new Object[]{});
        if (timeout < 0) {
            throw new InvalidParameterException(String.format(
                    "Timeout for annotation [%s] must be 0 or greater on [%s]",
                    expectedAnnotationClass.getName(),
                    targetMethodName
            ));
        }
        data.setTimeOut(timeout);
        
		final Method noreplyMethod = expectedAnnotationClass.getDeclaredMethod("noreply", new Class[]{});
		final Boolean noreply = (Boolean)noreplyMethod.invoke(annotation,new Object[]{});
		data.setNoreply(noreply);
		
		final Method invokeMethod = expectedAnnotationClass.getDeclaredMethod("invoke", new Class[]{});
		final Boolean invoke = (Boolean)invokeMethod.invoke(annotation,new Object[]{});
		data.setInvoke(invoke);
        
	}
    
    
    protected static void populateParamIndex(final AnnotationContext data,final JoinPoint jp,
			final Class<?> expectedAnnotationClass, final Method targetMethod)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Annotation[][] paramAnnotationArrays = targetMethod.getParameterAnnotations();
		Method mh=null;
		Integer order;
		String name;
		String beanName;
		if (paramAnnotationArrays != null && paramAnnotationArrays.length > 0) {
			for (int ix = 0; ix < paramAnnotationArrays.length; ix++) {
				final Annotation[] paramAnnotations = paramAnnotationArrays[ix];
				if (paramAnnotations != null && paramAnnotations.length > 0) {
					for (int jx = 0; jx < paramAnnotations.length; jx++) {
						final Annotation paramAnnotation = paramAnnotations[jx];
						if (expectedAnnotationClass.equals(paramAnnotation.annotationType())) {
					        mh = expectedAnnotationClass.getDeclaredMethod("order", new Class[]{});
					        order = (Integer) mh.invoke(paramAnnotation, new Object[]{});
					        data.getIndexOrder().put(ix, order);
					        
					        mh = expectedAnnotationClass.getDeclaredMethod("name", new Class[]{});
					        name = (String) mh.invoke(paramAnnotation, new Object[]{});
					        data.getIndexName().put(ix, name);
					        
					        mh = expectedAnnotationClass.getDeclaredMethod("beanName", new Class[]{});
					        beanName = (String) mh.invoke(paramAnnotation, new Object[]{});
					        data.getIndexBeanName().put(ix, beanName);
					        
					        data.getIndexObject().put(ix, jp.getArgs()[ix]);
					        
							
						}
					}
				}
			}
		}
	}
	



	public final CacheFactory getCacheFactory() {
		return cacheFactory;
	}

	@org.springframework.beans.factory.annotation.Autowired
	public final void setCacheFactory(@Qualifier("cacheFactory") CacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}



	public final CacheKeyProvider getCacheKeyProvider() {
		return cacheKeyProvider;
	}


	@org.springframework.beans.factory.annotation.Autowired
	public final void setCacheKeyProvider(@Qualifier("cacheKeyProvider") CacheKeyProvider cacheKeyProvider) {
		this.cacheKeyProvider = cacheKeyProvider;
	}


	public final ApplicationContext getApplicationContext() {
		return applicationContext;
	}


	public final void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	
}
