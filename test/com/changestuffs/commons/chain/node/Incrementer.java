package com.changestuffs.commons.chain.node;

import java.util.HashSet;
import java.util.Set;

import com.changestuffs.commons.chain.annotation.SubscribedChain;
import com.google.common.eventbus.Subscribe;

@SubscribedChain
public class Incrementer implements ChainNode{

	private int number = 0;
	
	@Override
	public boolean next() {
		return true;
	}

	@Override
	public void execute() {
		number++;
	}

	@Override
	public Set<Object> post() {
		Set<Object> set = new HashSet<Object>();
		set.add(number);
		return set;
	}
	
	@Subscribe
	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

}
