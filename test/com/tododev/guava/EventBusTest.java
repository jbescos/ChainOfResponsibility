package com.changestuffs.commons.guava;


import org.junit.Assert;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusTest {

	private final EventBus eventBus = new EventBus();
	private final String HELLO_WORD = "Hello word!";
	
	@Test
	public void testConcept(){
		Suscriber suscriber = new Suscriber();
		eventBus.register(suscriber);
		eventBus.post(HELLO_WORD);
		Assert.assertEquals(HELLO_WORD, suscriber.word);
		Assert.assertEquals(null, suscriber.privateWord);
	}
	
	private class Suscriber{
		
		private String word;
		private String privateWord;
		
		@Subscribe
		public void notified(String word){
			this.word = word;
		}
		
		@Subscribe // doesn't work
		private void privateNotified(String word){
			privateWord = word;
		}
		
	}
	
}
