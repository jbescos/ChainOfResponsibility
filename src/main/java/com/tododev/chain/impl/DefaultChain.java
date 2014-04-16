package com.tododev.chain.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.eventbus.EventBus;
import com.tododev.chain.ChainOfResponsibility;
import com.tododev.chain.node.ChainNode;
import com.tododev.chain.node.NodeResponse;
import com.tododev.chain.node.ResponseType;

/**
 * Default implementation of event chain of responsibility.
 * Any ChainNode can post objects to others ChainNodes
 * @author jbescos
 *
 */
public class DefaultChain implements ChainOfResponsibility{

	private final Log log = LogFactory.getLog(getClass());
	private final EventBus eventBus = new EventBus();
	private final List<ChainNode> nodes;
	
	/**
	 * Add an array of ChainNode. The order of execution is the same as the array.
	 * @param chainNodes
	 */
	public DefaultChain(ChainNode ... chainNodes){
		nodes = Arrays.asList(chainNodes);
		registerInEventbus(nodes);
	}
	
	private void registerInEventbus(List<ChainNode> nodes){
		for(ChainNode node : nodes){
			eventBus.register(node);
		}
	}
	
	/**
	 * Starts the chain. It will be finished when the chain is over or any
	 * ChainNode stops.
	 * The order of execution is:
	 * -Call execute method of the first ChainNode.
	 * -Post objects to event bus (id there are).
	 * -EventBus will set that objects in the ChainNodes that are subscribed.
	 * -Evaluate the ResponseType of ChainNode to continue with the next or not.
	 */
	@Override
	public void start() {
		boolean finish = false;
		int i = 0;
		log.info("Starting chain");
		while(!finish){
			ChainNode node = nodes.get(i);
			log.debug("Executing "+node);
			NodeResponse response = node.execute();
			log.debug("Response of is "+response);
			postObjects(response.getPost());
			if(response.getResponseType() == ResponseType.Continue){
				i++;
				if(i == nodes.size())
					finish = true;
			}else if(response.getResponseType() == ResponseType.Stop){
				finish = true;
			}
		}
		log.info("Chain has finished");
	}
	
	private void postObjects(List<Object> objects){
		for(Object obj : objects){
			eventBus.post(obj);
		}
	}

}
