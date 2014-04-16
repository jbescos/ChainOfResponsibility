package com.tododev.chain.node;

import java.util.Set;

import com.tododev.chain.node.ChainNode;

public class ChainLast implements ChainNode{

	private boolean invoked = false;
	
	@Override
	public void execute() {
		invoked = true;
	}

	@Override
	public boolean next() {
		return false;
	}

	@Override
	public Set<Object> post() {
		return null;
	}

	public boolean isInvoked() {
		return invoked;
	}
	
}
