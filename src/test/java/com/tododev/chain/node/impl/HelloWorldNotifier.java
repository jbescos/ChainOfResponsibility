package com.tododev.chain.node.impl;

import com.tododev.chain.node.ChainNode;
import com.tododev.chain.node.NodeResponse;
import com.tododev.chain.node.ResponseType;

public class HelloWorldNotifier implements ChainNode{

	public static final String HELLO_WORLD = "Hello World";
	
	@Override
	public NodeResponse execute() {
		return new NodeResponse.Builder(ResponseType.Continue).post(HELLO_WORLD).build();
	}

}
