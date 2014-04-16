package com.tododev.chain.core.impl;

import java.lang.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.eventbus.EventBus;
import com.tododev.chain.annotation.SubscribedChain;
import com.tododev.chain.core.ChainOfResponsibility;
import com.tododev.chain.node.ChainNode;


public class EventChainProccessor implements ChainOfResponsibility{

	private final EventBus eventBus;
	private Local local;
	private final ChainNode[] chain;
	
	public EventChainProccessor(ChainNode ... chain){
		this(new EventBus(), chain);
	}
	
	public EventChainProccessor(EventBus eventBus, ChainNode ... chain){
		this.eventBus = eventBus;
		this.chain = chain;
	}
	
	@Override
	public void start(){
		local = new Local(eventBus, chain);
		local.start();
	}
	
	private class Local{
		
		private final Log log = LogFactory.getLog(getClass());
		private final EventBus eventBus;
		private ChainWrapper first;
		private ChainWrapper lastElement;
		
		private Local(EventBus eventBus, ChainNode ... chain){
			this.eventBus = eventBus;
			initAllElements(chain);
		}
		
		private void start(){
			log.info("Chain has started");
			first.execute();
			log.info("Chain has finsihed");
		}
		
		private void initAllElements(ChainNode ... chain){
			if(chain == null)
				throw new RuntimeException("Chain is null");
			for(ChainNode element : chain){
				initElement(element);
			}
		}
		
		private void initElement(ChainNode element){
			ChainWrapper wrapper = new ChainWrapper(eventBus, element);
			Annotation annotation = element.getClass().getAnnotation(SubscribedChain.class);
			if(annotation != null && SubscribedChain.class == annotation.annotationType()){
				log.info("Registering to event bus: "+element);
				eventBus.register(element);
			}
			if(first == null){
				first = wrapper;
				lastElement = first;
			}else{
				fillChain(lastElement, wrapper);
				lastElement = wrapper;
			}
		}
		
		private void fillChain(ChainWrapper previusElement, ChainWrapper nextElement){
			previusElement.setNext(nextElement);
		}
		
	}
	
}
