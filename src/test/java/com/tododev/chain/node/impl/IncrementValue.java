package com.tododev.chain.node.impl;

import com.google.common.eventbus.Subscribe;
import com.tododev.chain.node.ChainNode;
import com.tododev.chain.node.NodeResponse;
import com.tododev.chain.node.ResponseType;

public class IncrementValue implements ChainNode{

	private int value = 0;
	public static int MAX_EXCECUTIONS = 10;
	private final ResponseType responseType;
	
	public IncrementValue(ResponseType responseType){
		this.responseType = responseType;
	}
	
	@Override
	public NodeResponse execute() {
		ResponseType responseType = this.responseType;
		value++;
		if(value == MAX_EXCECUTIONS){
			responseType = ResponseType.Stop;
		}
		return new NodeResponse.Builder(responseType).post(value).build();
	}
	
	@Subscribe
	public void setValue(Integer value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
