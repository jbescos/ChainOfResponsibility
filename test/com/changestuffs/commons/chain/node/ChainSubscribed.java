package com.changestuffs.commons.chain.node;

import java.util.Set;

import org.junit.Assert;

import com.changestuffs.commons.chain.annotation.SubscribedChain;
import com.changestuffs.commons.chain.node.ChainNode;
import com.google.common.eventbus.Subscribe;

@SubscribedChain
public class ChainSubscribed implements ChainNode{

	private int value;
	private boolean invoked = false;
	
	@Subscribe
	public void receive(Integer object) {
		value = object;
	}

	@Override
	public void execute() {
		Assert.assertEquals(ChainSendValue.VALUE, value);
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

	public int getValue() {
		return value;
	}

	public boolean isInvoked() {
		return invoked;
	}
	
}