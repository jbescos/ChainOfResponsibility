package com.changestuffs.commons.chain.core.impl;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.changestuffs.commons.chain.node.ChainNode;
import com.google.common.eventbus.EventBus;

class ChainWrapper {

	private final Log log = LogFactory.getLog(getClass());
	private ChainWrapper nextChain;
	private final EventBus eventBus;
	private final ChainNode chain;
	
	public ChainWrapper(EventBus eventBus, ChainNode chain){
		this.chain = chain;
		this.eventBus = eventBus;
	}
	
	final void setNext(ChainWrapper nextChain) {
		this.nextChain = nextChain;
	}
	
	void execute(){
		log.debug("Execute: "+chain);
		chain.execute();
		postAll(chain.post());
		if(chain.next() && nextChain!=null)
			nextChain.execute();
	}
	
	private void postAll(Set<Object> objects){
		if(objects != null){
			log.debug("Posting: "+objects);
			for(Object object : objects){
				post(object);
			}
		}
	}
	
	protected <T> void post(T object){
		eventBus.post(object);
	}

	@Override
	public String toString() {
		return "ChainWrapper [chain=" + chain + "]";
	}
	
}
