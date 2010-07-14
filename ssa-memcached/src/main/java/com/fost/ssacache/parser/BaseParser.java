package com.fost.ssacache.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;

import com.fost.ssacache.AnnotationContext;

/**
 * 
 * @author Janly
 *
 */
public abstract class BaseParser {
	
	
	public void parse(final AnnotationContext data,final Annotation annotation, final Class<?> expectedAnnotationClass,final String targetMethodName) throws Exception{
		parserCommon(data, annotation, expectedAnnotationClass, targetMethodName);
	}
	
	
	
	protected static void parserInvoke(final AnnotationContext data,final Annotation annotation, final Class<?> expectedAnnotationClass,final String targetMethodName) throws Exception{
		final Method invokeMethod = expectedAnnotationClass.getDeclaredMethod("invoke", new Class[]{});
		final Boolean invoke = (Boolean)invokeMethod.invoke(annotation,new Object[]{});
		data.setInvoke(invoke);
	}
	
	protected static void parserExptime(final AnnotationContext data,final Annotation annotation, final Class<?> expectedAnnotationClass,final String targetMethodName) throws Exception{
        final Method expirationMethod = expectedAnnotationClass.getDeclaredMethod("expiration", new Class[]{});
        final int expiration = (Integer) expirationMethod.invoke(annotation, new Object[]{});
        if (expiration < 0) {
            throw new InvalidParameterException(String.format("Expiration for annotation [%s] must be 0 or greater on [%s]",expectedAnnotationClass.getName(),targetMethodName
            ));
        }
        data.setExpiration(expiration);
	}


	protected static void parserCommon(final AnnotationContext data,final Annotation annotation, final Class<?> expectedAnnotationClass,final String targetMethodName) throws Exception{
		
		final Method namespaceMethod = expectedAnnotationClass.getDeclaredMethod("namespace", new Class[]{});
		final String namespace = (String) namespaceMethod.invoke(annotation,new Object[]{});
		data.setNamespace(namespace);
		
        final Method assignKeyMethod = expectedAnnotationClass.getDeclaredMethod("assignedKey", new Class[]{});
        final String assignKey = (String) assignKeyMethod.invoke(annotation, new Object[]{});
        data.setAssignedKey(assignKey);
        
		final Method noreplyMethod = expectedAnnotationClass.getDeclaredMethod("noreply", new Class[]{});
		final Boolean noreply = (Boolean)noreplyMethod.invoke(annotation,new Object[]{});
		data.setNoreply(noreply);
		
		
        final Method timeoutMethod = expectedAnnotationClass.getDeclaredMethod("timeout", new Class[]{});
        final int timeout = (Integer) timeoutMethod.invoke(annotation, new Object[]{});
        if (timeout < 0) {
            throw new InvalidParameterException(String.format("Timeout for annotation [%s] must be 0 or greater on [%s]",expectedAnnotationClass.getName(),targetMethodName
            ));
        }
        data.setTimeOut(timeout);	
	}
	
}
