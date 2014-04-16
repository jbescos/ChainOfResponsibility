package com.changestuffs.commons.chain.node;

import java.util.HashSet;
import java.util.Set;

import com.changestuffs.commons.chain.node.ChainNode;

public class ChainSendValue implements ChainNode{

	static final int VALUE = 2;
	private boolean invoked = false;
	
	@Override
	public void execute() {
		invoked = true;
	}

	@Override
	public boolean next() {
		return true;
	}

	@Override
	public Set<Object> post() {
		Set<Object> list = new HashSet<Object>();
		list.add(VALUE);
		System.out.println("Posting: "+list);
		return list;
	}

	public boolean isInvoked() {
		return invoked;
	}
	
	
	
}
