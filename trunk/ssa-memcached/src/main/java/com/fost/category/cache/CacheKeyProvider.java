/**
 * 
 */
package com.fost.category.cache;

/**
 * @author Janly
 *
 */
public interface CacheKeyProvider {
	
	
	
	public String generateCacheKey(AnnotationContext annotationContext);
  

	public CacheKeyStoreStrategy getCacheKeyStoreStrategy();
    

}
