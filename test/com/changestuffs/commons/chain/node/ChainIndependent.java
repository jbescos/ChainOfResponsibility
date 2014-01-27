package com.changestuffs.commons.chain.node;

import java.util.Set;

import com.changestuffs.commons.chain.node.ChainNode;

public class ChainIndependent implements ChainNode{

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
		return null;
	}

	public boolean isInvoked() {
		return invoked;
	}
	
}