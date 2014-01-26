package com.changestuffs.commons.chain.node;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.changestuffs.commons.chain.annotation.SubscribedChain;
import com.changestuffs.commons.chain.node.ChainNode;
import com.changestuffs.commons.chain.node.ChainTreeNode;
import com.google.common.eventbus.Subscribe;

@SubscribedChain
public class TreeNode implements ChainTreeNode{

	private ChainNode[] chain;
	private Integer integer;
	
	public TreeNode(ChainNode ... chain){
		this.chain = chain;
	}
	
	@Override
	public boolean next() {
		return true;
	}

	@Override
	public void execute() {
		integer = integer*10;
	}

	@Override
	public Set<Object> post() {
		Set<Object> values = new HashSet<Object>();
		values.add(integer);
		System.out.println(this+": posting "+integer);
		return values;
	}

	@Override
	public Collection<ChainNode> enqueueChain() {
		return Arrays.asList(chain);
	}

	public Integer getInteger() {
		return integer;
	}

	@Subscribe
	public void setInteger(Integer integer) {
		System.out.println(this+": receiving "+integer);
		this.integer = integer;
	}

}
