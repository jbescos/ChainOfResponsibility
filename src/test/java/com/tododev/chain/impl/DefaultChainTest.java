package com.tododev.chain.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tododev.chain.ChainOfResponsibility;
import com.tododev.chain.node.ResponseType;
import com.tododev.chain.node.impl.HelloWorldNotifier;
import com.tododev.chain.node.impl.HelloWorldReceiver;
import com.tododev.chain.node.impl.IncrementValue;

public class DefaultChainTest {

	private ChainOfResponsibility chain;
	
	@Test
	public void demo(){
		HelloWorldNotifier notifier = new HelloWorldNotifier();
		HelloWorldReceiver receiver = new HelloWorldReceiver();
		chain = new DefaultChain(notifier, receiver);
		chain.start();
		assertEquals(HelloWorldNotifier.HELLO_WORLD, receiver.getPhrase());
	}
	
	@Test
	public void passValuesForwardAndBackward(){
		IncrementValue v1 = new IncrementValue(ResponseType.Continue);
		IncrementValue v2 = new IncrementValue(ResponseType.Continue);
		IncrementValue v3 = new IncrementValue(ResponseType.Continue);
		chain = new DefaultChain(v1, v2, v3);
		chain.start();
		assertEquals(3, v1.getValue());
		assertEquals(3, v2.getValue());
		assertEquals(3, v3.getValue());
	}
	
	@Test
	public void repeatUntilMax(){
		IncrementValue v1 = new IncrementValue(ResponseType.Repeat);
		chain = new DefaultChain(v1);
		chain.start();
		assertEquals(IncrementValue.MAX_EXCECUTIONS, v1.getValue());
	}
	
	@Test
	public void stopsAtFirstNode(){
		IncrementValue v1 = new IncrementValue(ResponseType.Stop);
		IncrementValue v2 = new IncrementValue(ResponseType.Continue);
		IncrementValue v3 = new IncrementValue(ResponseType.Continue);
		chain = new DefaultChain(v1, v2, v3);
		chain.start();
		assertEquals(1, v1.getValue());
		assertEquals(1, v2.getValue());
		assertEquals(1, v3.getValue());
	}
	
}
