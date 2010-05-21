package com.fost.prop.client;

import com.fost.prop.api.model.Attribute;
import com.fost.prop.api.model.Node;

public class ToStringTest {
	public static void main(String[] args){
		Node node = new Node();
		node.setName("abc");
		System.out.println(node.toString());
		
		Attribute attr = new Attribute();
		System.out.println(attr.toString());
	}
}
