package com.tododev.chain.node;

import java.util.HashSet;
import java.util.Set;

import com.google.common.eventbus.Subscribe;
import com.tododev.chain.annotation.SubscribedChain;
import com.tododev.chain.node.ChainNode;

@SubscribedChain
public class PassTheInteger implements ChainNode{

	private Integer integer;
	
	public PassTheInteger(Integer integer){
		this.integer = integer;
	}
	
	@Override
	public boolean next() {
		return true;
	}

	@Override
	public void execute() {
		integer++;
	}

	@Override
	public Set<Object> post() {
		Set<Object> values = new HashSet<Object>();
		values.add(integer);
		System.out.println(this+": posting "+integer);
		return values;
	}

	@Subscribe
	public void setInteger(Integer integer) {
		System.out.println(this+": receiving "+integer);
		this.integer = integer;
	}

	public Integer getInteger() {
		return integer;
	}

}
