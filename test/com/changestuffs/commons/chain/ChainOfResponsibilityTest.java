package com.changestuffs.commons.chain;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.changestuffs.commons.chain.core.ChainOfResponsibility;
import com.changestuffs.commons.chain.core.impl.EventChainProccessor;
import com.changestuffs.commons.chain.node.ChainIndependent;
import com.changestuffs.commons.chain.node.ChainLast;
import com.changestuffs.commons.chain.node.ChainNode;
import com.changestuffs.commons.chain.node.ChainSendValue;
import com.changestuffs.commons.chain.node.ChainSubscribed;
import com.changestuffs.commons.chain.node.PassTheInteger;
import com.changestuffs.commons.chain.node.TreeNode;


public class ChainOfResponsibilityTest {
	
	private final ChainIndependent chain1 = new ChainIndependent();
	private final ChainSendValue chain2 = new ChainSendValue();
	private final ChainSubscribed chain3 = new ChainSubscribed();
	private final ChainLast chain4 = new ChainLast();
	
	private final ChainOfResponsibility proccessor = new EventChainProccessor(chain1, chain2, chain3);
	private final ChainOfResponsibility proccessor2 = new EventChainProccessor(chain1, chain4, chain3);
	
	@Test
	public void testChain(){
		proccessor.start();
		Assert.assertTrue(chain1.isInvoked());
		Assert.assertTrue(chain2.isInvoked());
		Assert.assertTrue(chain3.isInvoked());
	}
	
	@Test
	public void testProccessor2(){
		proccessor2.start();
		Assert.assertTrue(chain1.isInvoked());
		Assert.assertTrue(chain4.isInvoked());
		Assert.assertFalse(chain3.isInvoked());
	}
	
	@Test(expected = RuntimeException.class)
	public void noChains(){
		@SuppressWarnings("unused")
		EventChainProccessor proccessor = new EventChainProccessor((ChainNode[])null);
	}
	
	@Test
	public void treeChain(){
		/*
		 * Order: node -> treeNode1 -> node -> node -> treeNode2 -> node -> treeNode1 -> node -> node
		 * Values: ((1)*10+1+1)*10+1)*10+1+1 = 1212
		 */
		PassTheInteger node = new PassTheInteger(0);
		TreeNode treeNode1 = new TreeNode(node, node);
		TreeNode treeNode2 = new TreeNode(node, treeNode1);
		ChainOfResponsibility proccessor = new EventChainProccessor(node, treeNode1, treeNode2);
		proccessor.start();
		assertEquals(new Integer(1212), node.getInteger());
	}
	
}
