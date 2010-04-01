/**
 * 
 */
package com.fost.ssacache;

/**
 * @author Janly
 *
 */
public interface CacheKeyProvider {
	
	
	
	public String generateCacheKey(AnnotationContext annotationContext);
  

	public CacheKeyStoreStrategy getCacheKeyStoreStrategy();
    

}
