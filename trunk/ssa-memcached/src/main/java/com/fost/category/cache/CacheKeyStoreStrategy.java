/**
 * 
 */
package com.fost.category.cache;

import java.lang.reflect.Method;

/**
 * @author Janly
 *
 */
public interface CacheKeyStoreStrategy {

	public void add(Class<?> key, Method value);

	public Method find(Class<?> key);
}
