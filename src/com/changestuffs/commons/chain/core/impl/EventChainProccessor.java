package com.changestuffs.commons.chain.core.impl;

import java.lang.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.changestuffs.commons.chain.annotation.SubscribedChain;
import com.changestuffs.commons.chain.core.ChainOfResponsibility;
import com.changestuffs.commons.chain.node.ChainNode;
import com.changestuffs.commons.chain.node.ChainTreeNode;
import com.google.common.eventbus.EventBus;


public class EventChainProccessor implements ChainOfResponsibility{

	private final Log log = LogFactory.getLog(getClass());
	private final EventBus eventBus = new EventBus();
	private ChainWrapper first;
	private ChainWrapper lastElement;
	
	public EventChainProccessor(ChainNode ... chain){
		initAllElements(chain);
	}
	
	private void initAllElements(ChainNode ... chain){
		if(chain == null)
			throw new RuntimeException("Chain is null");
		for(ChainNode element : chain){
			initElement(element);
			if(element instanceof ChainTreeNode){
				ChainTreeNode multi = (ChainTreeNode)element;
				ChainNode[] subnodes = multi.enqueueChain().toArray(new ChainNode[multi.enqueueChain().size()]);
				initAllElements(subnodes);
			}
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
	
	@Override
	public void start(){
		log.info("Chain has started");
		first.execute();
		log.info("Chain has finsihed");
	}
	
}
