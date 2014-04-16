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

public class DefaultChain implements ChainOfResponsibility{

	private final Log log = LogFactory.getLog(getClass());
	private final EventBus eventBus = new EventBus();
	private final List<ChainNode> nodes;
	
	public DefaultChain(ChainNode ... chainNodes){
		nodes = Arrays.asList(chainNodes);
		registerInEventbus(nodes);
	}
	
	private void registerInEventbus(List<ChainNode> nodes){
		for(ChainNode node : nodes){
			eventBus.register(node);
		}
	}
	
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
