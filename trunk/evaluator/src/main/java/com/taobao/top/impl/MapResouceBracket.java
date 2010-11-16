package com.taobao.top.impl;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.taobao.top.EvaluateException;
import com.taobao.top.PlaceHolder;
import com.taobao.top.bracket.AbstractPriorityBracket;
import com.taobao.top.operator.Operator;
import com.taobao.top.operator.base.DoubleListOperator;
import com.taobao.top.operator.base.DoubleOperator;
import com.taobao.top.operator.base.NumberOperator;
import com.taobao.top.operator.base.StringOperator;


/**
 * 
 * @author zijiang.jl
 *
 *
 *
 */
public class MapResouceBracket extends AbstractPriorityBracket<Map<String,Object>,Properties>{
	/**
	 * 解析变量
	 */
	@Override
	protected Operator<?> resolveVarPlaceHolder(Operator<?> value) throws EvaluateException{
		if(value instanceof StringOperator){
			StringOperator so=(StringOperator)value;
			if(so.value().startsWith(PlaceHolder.instance.getVarPlaceHolder())&&
					so.value().endsWith(PlaceHolder.instance.getVarPlaceHolder())){
				String temp=so.value().substring(1,so.value().length()-1);
				if(this.property!=null&&this.property.get(temp)!=null){
					String key=this.property.get(temp).toString().trim();
					return this.smartWrapResource(resource.get(key));
				}else{
					return this.smartWrapResource(resource.get(temp));
				}

			} 
		}                               
		return value;
	}
	
	
	
	private Operator<?> smartWrapResource(Object obj){
		if(obj instanceof Operator<?>) return (Operator<?>)obj;
		
		if(org.apache.commons.lang.math.NumberUtils.isNumber(obj.toString().trim())){
			return new DoubleOperator(obj.toString().trim());
		}
		
		if(obj instanceof String){
			String[] str=obj.toString().trim().split(PlaceHolder.instance.getSplitPlaceHolder());
			if(str.length==1) return new StringOperator(obj.toString().trim());
			java.util.List<Double> li=new java.util.ArrayList<Double>();
			for(int i=0;i<str.length;i++){
				li.add(Double.parseDouble(str[i].trim()));
			}
			return new DoubleListOperator(li);
		}
		
		if(obj instanceof Number){
			return new NumberOperator().setValue((Number)obj);
		}
		
		
		if(obj instanceof double[]){
			double[] d=(double[])obj;
			List<Double> li=new java.util.ArrayList<Double>();
			for(int i=0;i<d.length;i++){
				li.add(d[i]);
			}
			return new DoubleListOperator().setValue(li);
		}
		
		if(obj instanceof List<?>){
			List<?> list=(List<?>)obj;
			List<Double> li=new java.util.ArrayList<Double>();
			for(int i=0;i<list.size();i++){
				if(list.get(i) instanceof Double) li.add((Double)list.get(i));
			}
			return new DoubleListOperator().setValue(li);
			
		}
		
		return Operator.nullInstance;
		
	}
}
