/**
 * 
 */
package com.fost.category.cache.key;

import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fost.category.cache.annotation.CacheKey;
import com.fost.category.cache.AnnotationContext;
import com.fost.category.cache.CacheKeyStoreStrategy;
import com.fost.category.cache.CacheKeyProvider;;

/**
 * @author Janly
 *
 */
public final class DefaultCacheKeyProvider implements CacheKeyProvider{
	private static final String SEPARATOR = ":";
	private static final int _NAMESPACELENGTH_ = 100;
	private static final int _KEYLENGTH_ = 250;
	public static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	private CacheKeyStoreStrategy cacheKeyStoreStrategy;
	
	
	@Override
	public CacheKeyStoreStrategy getCacheKeyStoreStrategy() {
		return this.cacheKeyStoreStrategy;
	}
	
	
	@org.springframework.beans.factory.annotation.Autowired
	public final void setCacheKeyStoreStrategy(
			@Qualifier("cacheKeyStoreStrategy") CacheKeyStoreStrategy cacheKeyStoreStrategy) {
		this.cacheKeyStoreStrategy = cacheKeyStoreStrategy;
	}

	
	
	@Override
	public String generateCacheKey(AnnotationContext annotationContext) {
		java.lang.StringBuilder sb=new java.lang.StringBuilder(annotationContext.getNamespace().replaceAll(" ", ""));
		int namespaceLen=sb.toString().length();
		if(namespaceLen>=_NAMESPACELENGTH_) throw new java.lang.RuntimeException("The namespace is too length,the max value is "+_NAMESPACELENGTH_);
		
		if(annotationContext.getAssignedKey()!=null&&
				!annotationContext.getAssignedKey().equals("")) 
			return sb.append(annotationContext.getAssignedKey().replaceAll(" ", "")).toString();

		java.util.Map<Integer, Object> objectmap=annotationContext.getIndexObject();
		java.util.Map<Integer, String> nameMap=annotationContext.getIndexName();
		java.util.Map<Integer, String> beanNameMap=annotationContext.getIndexBeanName();
		
		java.lang.StringBuilder key=new java.lang.StringBuilder("");
		for(java.util.Iterator<Integer> it=objectmap.keySet().iterator();it.hasNext();){
			Integer in=it.next();

			key.append(SEPARATOR);
			key.append(nameMap.get(in));
			
			Object paramObj=null;
			Object beanObj=null;
			
			String beanName=beanNameMap.get(in);
			if(beanName!=null&&!"".equals(beanName)){
				try {
					Class<?> clazz=Thread.currentThread().getContextClassLoader().loadClass(beanName);
					beanObj=clazz.newInstance();
					paramObj=objectmap.get(in);
				} catch (java.lang.Throwable t) {
					beanObj=objectmap.get(in);
				}
			}else{
				beanObj=objectmap.get(in);
			}
			key.append(this.generateCacheKey(beanObj,paramObj));
		}
		

		int keyLen=_KEYLENGTH_-namespaceLen;
		String temp=key.toString().replaceAll(" ", "");
		if(temp.length()>keyLen){
			temp=md5(temp);
		}
		
		return sb.append(temp).toString();
	}


	private static String md5(String value){
		String temp=value;
		try {
			java.security.MessageDigest md=java.security.MessageDigest.getInstance("MD5");
			md.reset();
			md.update(value.getBytes());
			byte[] bt=md.digest();
			return toHexString(bt);
		} catch (java.lang.Throwable t) {
		}
		return temp;
	}
	
	
	public static String toHexString(byte[] b) {   
       StringBuilder sb = new StringBuilder(b.length * 2);   
       for (int i = 0; i < b.length; i++) {   
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);   
            sb.append(hexChar[b[i] & 0x0f]);   
       }   
        return sb.toString();   
	}  
	
	
	private String generateCacheKey(final Object beanObject,final Object paramObject) {
        try {
            final Method keyMethod = getKeyMethod(beanObject,paramObject);
            return generateObjectId(keyMethod, beanObject,paramObject);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
	}

	
	private Method getKeyMethod(final Object beanObject,final Object paramObject) throws NoSuchMethodException {
        final Method storedMethod = cacheKeyStoreStrategy.find(beanObject.getClass());
        if (storedMethod != null) { return storedMethod; }
        final Method[] methods = beanObject.getClass().getDeclaredMethods();
        Method targetMethod = null;
        for (final Method method : methods) {
            if (method != null && method.getAnnotation(CacheKey.class) != null) {
                if (method.getParameterTypes().length!=0&&method.getParameterTypes().length!=1) {
                    throw new RuntimeException(String.format(
                            "Method [%s] must have 0 or 1 arguments to be annotated with [%s]",
                            method.toString(),
                            CacheKey.class.getName()));
                }
                
                if (!String.class.equals(method.getReturnType())) {
                    throw new RuntimeException(String.format(
                            "Method [%s] must return a String to be annotated with [%s]",
                            method.toString(),
                            CacheKey.class.getName()));
                }
                if (targetMethod != null) {
                    throw new RuntimeException(String.format(
                            "Class [%s] should have only one method annotated with [%s]. See [%s] and [%s]",
                            beanObject.getClass().getName(),
                            CacheKey.class.getName(),
                            targetMethod.getName(),
                            method.getName()));
                }
                targetMethod = method;
            }
        }

        if (targetMethod == null) {
            targetMethod = beanObject.getClass().getMethod("toString", new Class[]{});
        }
        
        if(targetMethod.getParameterTypes().length==1){
        	if(!paramObject.getClass().equals(targetMethod.getParameterTypes()[0])){
                throw new RuntimeException(String.format(
                        "Method [%s] must have  1 arguments [%s] to be annotated with [%s]",
                        targetMethod.toString(),
                        targetMethod.getParameterTypes()[0].getName(),
                        CacheKey.class.getName()));
        	}
        }

        cacheKeyStoreStrategy.add(beanObject.getClass(), targetMethod);

        return targetMethod;
    }

    private String generateObjectId(final Method keyMethod, final Object beanObject,final Object paramObject) throws Exception {
    	String objectId;
    	if(keyMethod.getParameterTypes().length==1){
    		objectId=(String) keyMethod.invoke(beanObject, new Object[]{paramObject});
    	}else{
    		objectId=(String) keyMethod.invoke(beanObject, new Object[]{});
    	}
    	
        if (objectId == null || objectId.length() < 1) {
            throw new RuntimeException("Got an empty key value from " + keyMethod.getName());
        }
        return objectId;
    }

	
	
	
}
