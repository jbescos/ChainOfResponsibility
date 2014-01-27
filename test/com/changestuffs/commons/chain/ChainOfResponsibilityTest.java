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
import com.changestuffs.commons.chain.node.Incrementer;
import com.google.common.eventbus.EventBus;


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
		proccessor.start();
	}
	
	@Test
	public void incremental(){
		Incrementer node = new Incrementer();
		EventBus bus = new EventBus();
		ChainOfResponsibility proccessor1 = new EventChainProccessor(bus, node, node, node);
		ChainOfResponsibility proccessor2 = new EventChainProccessor(bus, node, node);
		proccessor1.start();
		proccessor1.start();
		proccessor2.start();
		assertEquals(8, node.getNumber());
	}
	
}
