/**
 * 
 */
package com.fost.ssacache.parser;

import java.lang.annotation.Annotation;

import com.fost.ssacache.AnnotationContext;

/**
 * @author Janly
 *
 */
public final class AddToCacheParser extends BaseParser{

	@Override
	public void parse(AnnotationContext data, Annotation annotation,
			Class<?> expectedAnnotationClass, String targetMethodName)
			throws Exception {
		super.parse(data, annotation, expectedAnnotationClass, targetMethodName);
		BaseParser.parserExptime(data, annotation, expectedAnnotationClass, targetMethodName);
	}

	
}
