/**
 * 
 */
package com.fost.ssacache.parser;

import com.fost.ssacache.annotation.AddToCache;
import com.fost.ssacache.annotation.AppendToCache;
import com.fost.ssacache.annotation.DeleteFromCache;
import com.fost.ssacache.annotation.GetFromCache;
import com.fost.ssacache.annotation.PrependToCache;
import com.fost.ssacache.annotation.ReadFromCache;
import com.fost.ssacache.annotation.ReplaceInCache;
import com.fost.ssacache.annotation.SetToCache;

/**
 * @author Janly
 *
 */
public final class ParserFactory {
	private static java.util.Map<Class<?>, BaseParser> map=new java.util.HashMap<Class<?>, BaseParser>();
	static{
		map.put(ParserFactory.class, new DefaultParser());
		map.put(AddToCache.class, new AddToCacheParser());
		map.put(AppendToCache.class, new DefaultParser());
		map.put(DeleteFromCache.class, new GetFromCacheParser());
		map.put(GetFromCache.class, new GetFromCacheParser());
		map.put(PrependToCache.class, new DefaultParser());
		map.put(ReadFromCache.class, new AddToCacheParser());
		map.put(ReplaceInCache.class, new DefaultParser());
		map.put(SetToCache.class, new AddToCacheParser());
	}
	
	public static BaseParser createParser(final Class<?> expectedAnnotationClass){
		BaseParser parser=map.get(expectedAnnotationClass);
		if(parser==null) map.get(ParserFactory.class);
		return parser;
	}
}
